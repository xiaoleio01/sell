package com.shawlen.sell.service.impl;

import com.shawlen.sell.dataobject.ProductInfo;
import com.shawlen.sell.dto.CartDTO;
import com.shawlen.sell.enums.ProductStatusEnum;
import com.shawlen.sell.enums.ResultEnum;
import com.shawlen.sell.exception.SellException;
import com.shawlen.sell.repository.ProductInfoRepository;
import com.shawlen.sell.service.ProductInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 *
 */
@Service
public class ProductInfoServiceImpl implements ProductInfoService {

    @Autowired
    private ProductInfoRepository repository;

    @Override
    @Cacheable(cacheNames = "productInfo",key = "123")
    public ProductInfo findOne(String productId) {
        return repository.findOne(productId);
    }

    @Override
    public List<ProductInfo> findUpAll() {
        //对在架和下架创建枚举。
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    @Override
    @CachePut(cacheNames = "productInfo",key = "123")
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO :cartDTOList) {
            ProductInfo productInfo = repository.findOne(cartDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock()+cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO :cartDTOList){
           ProductInfo productInfo =  repository.findOne(cartDTO.getProductId());
           if(productInfo == null){
              throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
           }

           Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
           if(result < 0 ){
               throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
           }

           productInfo.setProductStock(result);
           repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo up(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if(productInfo.getProductStatusEnum() == ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        productInfo.setProductStatus(0);
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo down(String productId) {
        ProductInfo productInfo = repository.findOne(productId);
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }

        if(productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }

        productInfo.setProductStatus(1);
        return repository.save(productInfo);
    }
}
