package com.shawlen.sell.service.impl;


import com.shawlen.sell.dataobject.SellerInfo;
import com.shawlen.sell.repository.SellerInfoRepository;
import com.shawlen.sell.service.SellerService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * @author ShawLen
 * @date 2019/11/19 4:04 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SellerServiceImplTest {

    private final String openid = "123456";

    @Autowired
    private SellerServiceImpl sellerService;

    @Test
    public void findSellerInfoByOpenid() {
        SellerInfo sellerInfo = sellerService.findSellerInfoByOpenid(openid);
        Assert.assertEquals(openid,sellerInfo.getOpenid());
    }
}
