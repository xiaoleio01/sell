package com.shawlen.sell.service.impl;


import com.shawlen.sell.dataobject.ImgsCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author ShawLen
 * @date 2019/12/9 9:58 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ImgsCategoryServiceImplTest {

    @Autowired
    private ImgsCategoryServiceImpl imgsCategoryService;

    @Test
    public void findByProductId() {
        List<ImgsCategory> imgsList = imgsCategoryService.findByProductId("123456");
        Assert.assertNotEquals(0,imgsList.size());
    }
}
