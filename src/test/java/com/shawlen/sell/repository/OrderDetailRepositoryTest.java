package com.shawlen.sell.repository;

import com.shawlen.sell.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author ShawLen
 * @date 2019/10/31 2:50 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private  OrderDetailRepository repository;

    @Test
    public void save(){
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("123456789");
        orderDetail.setProductIcon("http://vvvvv.jpg");
        orderDetail.setProductName("满汉全席");
        orderDetail.setProductPrice(new BigDecimal(188));
        orderDetail.setProductId("22222");
        orderDetail.setOrderId("1234567");
        orderDetail.setProductQuantity(10);

        OrderDetail result = repository.save(orderDetail);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByOrderId() {
        List<OrderDetail> orderDetailList = repository.findByOrderId("1234567");
        Assert.assertNotEquals(0,orderDetailList.size());

    }
}
