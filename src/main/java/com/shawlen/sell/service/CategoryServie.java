package com.shawlen.sell.service;

import com.shawlen.sell.dataobject.ProductCategory;

import java.util.List;

public interface CategoryServie {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    ProductCategory save(ProductCategory productCategory);
}
