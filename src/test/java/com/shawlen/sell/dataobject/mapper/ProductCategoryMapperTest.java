package com.shawlen.sell.dataobject.mapper;

import com.google.gson.internal.$Gson$Preconditions;
import com.shawlen.sell.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * @author ShawLen
 * @date 2019/11/20 4:54 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryMapperTest {

    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() {
        Map<String,Object> map = new HashMap<>();
        map.put("category_name","鹰潭小吃");
        map.put("category_type",188);
        int result = mapper.insertByMap(map);
        Assert.assertNotEquals(0,result);
    }

    @Test
    public void insertByObject() {
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryType(199);
        productCategory.setCategoryName("鹰潭小吃");
        int result = mapper.insertByObject(productCategory);
        Assert.assertNotEquals(1,result);
    }

    @Test
    public void findByCategoryType() {
        ProductCategory productCategory = mapper.findByCategoryType(188);
        Assert.assertNotNull(productCategory);
    }

    @Test
    public void findByCategoryName() {
        List<ProductCategory> productCategorys = mapper.findByCategoryName("鹰潭小吃");
        Assert.assertNotEquals(0,productCategorys.size());
    }

    @Test
    public void updateBycategoryType() {
        int result = mapper.updateBycategoryType("鹰潭快餐",188);
        Assert.assertEquals(1,result);
    }

    @Test
    public void deleteBycategoryType() {
        int result = mapper.deleteBycategoryType(188);
        Assert.assertEquals(1,result);
    }

    @Test
    public void selectByCategoryType() {
        ProductCategory productCategory = mapper.selectByCategoryType(188);
        Assert.assertNotNull(productCategory);
    }
}
