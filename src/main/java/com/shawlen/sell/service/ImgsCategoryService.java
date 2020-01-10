package com.shawlen.sell.service;

import com.shawlen.sell.dataobject.ImgsCategory;

import java.util.List;

/**
 * @author ShawLen
 * @date 2019/12/9 9:53 下午
 */
public interface ImgsCategoryService {

    /**
     * 根据商品id获取所有该商品的图片。
     * @param productId
     * @return
     */
    List<ImgsCategory> findByProductId(String productId);

    /**
     * 获取滚动图片
     */
    List<ImgsCategory> getRollImgs();
}
