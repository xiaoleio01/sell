package com.shawlen.sell.service.impl;

import com.shawlen.sell.dataobject.ImgsCategory;
import com.shawlen.sell.repository.ImgsCategoryRepository;
import com.shawlen.sell.service.ImgsCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author ShawLen
 * @date 2019/12/9 9:55 下午
 */
@Service
public class ImgsCategoryServiceImpl implements ImgsCategoryService {

    @Autowired
    private ImgsCategoryRepository repository;

    @Override
    public List<ImgsCategory> findByProductId(String productId) {
        return repository.findByProductId(productId);
    }

    @Override
    public List<ImgsCategory> getRollImgs() {
        return repository.findByImgCategory("3");
    }


}
