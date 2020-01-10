package com.shawlen.sell.repository;


import com.shawlen.sell.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author ShawLen
 * @date 2019/10/31 2:51 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    @Test
    public void save(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setBuyerOpenid("110");
        orderMaster.setBuyerName("十三叔");
        orderMaster.setBuyerAddress("普城市");
        orderMaster.setBuyerPhone("5680429");
        orderMaster.setOrderAmount(new BigDecimal(888.8));
        orderMaster.setOrderId("1234567");
        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);

    }

    @Test
    public void findByBuyerOpenid() {
        PageRequest request = new PageRequest(0,1);
        Page<OrderMaster> orderMasterPage = repository.findByBuyerOpenid("110",request);
        System.out.println(orderMasterPage.getTotalElements());
    }
}
