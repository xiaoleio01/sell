package com.shawlen.sell.repository;

import com.shawlen.sell.dataobject.ProductInfo;
import com.shawlen.sell.service.impl.ProductInfoServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author ShawLen
 * @date 2019/12/2 4:56 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository repository;

    @Test
    public void findByProductStatus() {
        ProductInfo productInfo = repository.findOne("123456");
        Assert.assertNotNull(productInfo);
    }

    @Test
    public  void findIne(){
        ProductInfo productInfo = repository.findOne("123456");
        Assert.assertNotNull(productInfo);
    }
}
