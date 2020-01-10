package com.shawlen.sell.service.impl;

import com.shawlen.sell.dataobject.ProductInfo;
import com.shawlen.sell.enums.ProductStatusEnum;
import com.shawlen.sell.repository.ProductInfoRepository;
import com.shawlen.sell.service.ProductInfoService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoServiceImplTest {

    @Autowired
    private ProductInfoServiceImpl productInfoService;

    @Test
    public void findOne() {
        ProductInfo productInfo = productInfoService.findOne("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    public void findUpAll() {
        List<ProductInfo> productInfoList = productInfoService.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll() {
        PageRequest request = new PageRequest(0,2);
        Page<ProductInfo> productInfoPage = productInfoService.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
    }

    @Test
    public void up() {
        ProductInfo productInfo = productInfoService.up("234567");
        Assert.assertNotNull(productInfo);
    }
}
