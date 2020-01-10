package com.shawlen.sell.service.impl;

import com.shawlen.sell.dataobject.OrderDetail;
import com.shawlen.sell.dto.OrderDTO;
import com.shawlen.sell.enums.OrderStatusEnum;
import com.shawlen.sell.enums.PatStatusEnum;
import com.shawlen.sell.enums.ResultEnum;
import com.shawlen.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author ShawLen
 * @date 2019/10/31 8:48 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    private final String BUYER_OPENID = "1234567890";

    private final String ORDER_ID = "1572527627884894248";

    @Autowired
    private OrderServiceImpl orderService;


    @Test
    public void create() {

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("十三叔");
        orderDTO.setBuyerAddress("厦门");
        orderDTO.setBuyerPhone("5680429");
        orderDTO.setBuyerOpenid(BUYER_OPENID);

        List<OrderDetail> orderDetailList =new ArrayList<>();
        OrderDetail o1 = new OrderDetail();
        o1.setProductId("234567");
        o1.setProductQuantity(2);
        orderDetailList.add(o1);

        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderService.create(orderDTO);
        log.info("创建商品成功：{}",result);


    }

    @Test
    public void findOne() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        Assert.assertNotNull(orderDTO);
    }

    @Test
    public void findList() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(BUYER_OPENID,request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalPages());
    }

    @Test
    public void cancel() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.cancel(orderDTO);
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
    }

    @Test
    public void finish() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.finish(orderDTO);
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());

    }

    @Test
    public void paid() {
        OrderDTO orderDTO = orderService.findOne(ORDER_ID);
        OrderDTO result = orderService.paid(orderDTO);
        Assert.assertEquals(PatStatusEnum.SUCCESS.getCode(),orderDTO.getPayStatus());
    }

    @Test
    public void List() {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderService.findList(request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalPages());
    }
}
