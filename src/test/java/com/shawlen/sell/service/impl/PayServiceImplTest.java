package com.shawlen.sell.service.impl;

import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.shawlen.sell.dto.OrderDTO;
import com.shawlen.sell.service.OrderService;
import com.shawlen.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author ShawLen
 * @date 2019/11/8 3:59 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payService;

    @Autowired
    private OrderService orderService;

    @Test
    public void create() {
        OrderDTO orderDTO = orderService.findOne("1572857980267483196");
        payService.create(orderDTO);
    }

    @Test
    public void refund(){
        OrderDTO orderDTO = orderService.findOne("1572857980267483188");
        payService.refund(orderDTO);
    }
}
