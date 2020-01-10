package com.shawlen.sell.service;

import com.shawlen.sell.dataobject.ProductInfo;
import com.shawlen.sell.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProductInfoService {

    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询所有
     * @param pageable      分页
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
     void increaseStock(List<CartDTO> cartDTOList);

     //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    /** 上架 */
    ProductInfo up(String productId);

    /** 下架 */
    ProductInfo down(String productId);
}
