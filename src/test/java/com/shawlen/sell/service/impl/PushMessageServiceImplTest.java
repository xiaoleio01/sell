package com.shawlen.sell.service.impl;

import com.shawlen.sell.dto.OrderDTO;
import com.shawlen.sell.service.OrderService;
import com.shawlen.sell.service.PushMessageService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author ShawLen
 * @date 2019/11/20 2:34 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageService pushMessageService;

    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatus() {
        OrderDTO orderDTO = orderService.findOne("1573543089331708606");
        pushMessageService.orderStatus(orderDTO);
    }
}
