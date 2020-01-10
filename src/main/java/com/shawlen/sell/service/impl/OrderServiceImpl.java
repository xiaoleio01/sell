package com.shawlen.sell.service.impl;

import com.shawlen.sell.converter.OrederMaster2OrderDTOConverter;
import com.shawlen.sell.dataobject.OrderDetail;
import com.shawlen.sell.dataobject.OrderMaster;
import com.shawlen.sell.dataobject.ProductInfo;
import com.shawlen.sell.dto.CartDTO;
import com.shawlen.sell.dto.OrderDTO;
import com.shawlen.sell.enums.OrderStatusEnum;
import com.shawlen.sell.enums.PatStatusEnum;
import com.shawlen.sell.enums.ResultEnum;
import com.shawlen.sell.exception.SellException;
import com.shawlen.sell.repository.OrderDetailRepository;
import com.shawlen.sell.repository.OrderMasterRepository;
import com.shawlen.sell.service.*;
import com.shawlen.sell.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;


import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ShawLen
 * @date 2019/10/31 7:49 下午
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Autowired
    private ProductInfoService productInfoService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Autowired
    private PayService payService;

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    public OrderDTO create(OrderDTO orderDTO) throws SellException {
        String orderId = KeyUtil.genUniqueKey();
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //1.查询商品（数量，价格）
        for(OrderDetail orderDetail : orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productInfoService.findOne(orderDetail.getProductId());
            if(productInfo == null ) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2.计算总价        计算某一件商品总价再加上总价。最终订单总价
            orderAmount = productInfo.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库

            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
        }

        //3.写入订单数据库（orderMaster和orderDetail）
        OrderMaster orderMaster = new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMaster.setPayStatus(PatStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //4.扣库存
        //labda表达式
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity())).collect(Collectors.toList());
        productInfoService.decreaseStock(cartDTOList);

        //发生webSocket推送消息
        webSocket.sendMessage("有新的订单");

        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.findOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        //判断该list是否为空
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        OrderDTO orderDTO = OrederMaster2OrderDTOConverter.converter(orderMaster);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);

        List<OrderDTO> orderDTOList = OrederMaster2OrderDTOConverter.converter(orderMasterPage.getContent());

       return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【取消订单】订单状态不正确！OrederId={},OrderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【取消订单】更新失败！orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        //返回库存
        if (CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("【取消订单】订单中无商品详情，orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream().map(
                e -> new CartDTO(e.getProductId(),e.getProductQuantity())
        ).collect(Collectors.toList());
        productInfoService.increaseStock(cartDTOList);


        //对已支付的订单退款。
        if (orderDTO.getPayStatus().equals(PatStatusEnum.SUCCESS.getCode())){
            payService.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【完结订单】订单状态错误！OrederId={},OrderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【完结订单】更新失败！orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }

        //推送微信模版消息
        pushMessageService.orderStatus(orderDTO);

        return orderDTO;
    }

    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("【支付订单】订单状态错误！OrederId={},OrderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PatStatusEnum.WAIT.getCode())){
            log.error("【支付订单】订单支付状态不正确，OrederId={},PayStatus={}",orderDTO.getOrderId(),orderDTO.getPayStatus());
            throw new SellException(ResultEnum.Pay_STATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PatStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("【支付订单】更新失败！orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_ERROR);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        //查询所有订单，分页查询
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);

        List<OrderDTO> orderDTOList = OrederMaster2OrderDTOConverter.converter(orderMasterPage.getContent());

        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }
}
