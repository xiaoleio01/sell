package com.shawlen.sell.dto;

import lombok.Data;

/**
 * @author ShawLen
 * @date 2019/10/31 8:28 下午
 */
@Data
public class CartDTO {

    /**
     * 商品ID
     */
    private String productId;

    /** 数量 */
    private Integer productQuantity;

    public CartDTO() {
    }

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
