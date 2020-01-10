package com.shawlen.sell.repository;

import com.shawlen.sell.dataobject.SellerInfo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author ShawLen
 * @date 2019/11/19 3:53 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerInfoRepositoryTest {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Test
    public void findByOpenid() {
        SellerInfo sellerInfo = sellerInfoRepository.findByOpenid("123456");
        Assert.assertNotNull(sellerInfo);
    }
}
