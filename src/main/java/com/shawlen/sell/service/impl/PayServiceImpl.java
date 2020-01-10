package com.shawlen.sell.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.shawlen.sell.dto.OrderDTO;
import com.shawlen.sell.enums.ResultEnum;
import com.shawlen.sell.exception.SellException;
import com.shawlen.sell.service.OrderService;
import com.shawlen.sell.service.PayService;
import com.shawlen.sell.utils.JsonUtil;
import com.shawlen.sell.utils.MathUtil;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.criterion.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * @author ShawLen
 * @date 2019/11/7 2:41 下午
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderService;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        payRequest.setOrderName(ORDER_NAME);
        log.info("【微信支付】发起支付，request={}", JsonUtil.toJson(payRequest));
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起支付，Response={}",JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】异步通知，Response={}",JsonUtil.toJson(payResponse));

        OrderDTO orderDTO = orderService.findOne(payResponse.getOrderId());
        if(orderDTO == null){
            log.error("【微信支付】异步通知，订单不存在，orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        //orderDTO.getOrderAmount().compareTo(new BigDecimal(payResponse.getOrderAmount())) != 0  这样比较金额double有可能造成精度问题。
        if(!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
            log.error("【微信支付】异步通知，订单金额不一致，orderId={},微信通知金额={},系统订单金额={}",
                    payResponse.getOrderId(),payResponse.getOrderAmount(),orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.PAY_NOTIFY_MONEY_VERIFY_ERROR);
        }

        //修改支付状态
        orderService.paid(orderDTO);

        return  payResponse;
    }

    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("【微信退款】reques={}",JsonUtil.toJson(refundRequest));

        //掉用SDK
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】response={}",JsonUtil.toJson(refundResponse));

        return refundResponse;
    }
}
