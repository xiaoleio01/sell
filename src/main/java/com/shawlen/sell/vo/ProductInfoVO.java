package com.shawlen.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shawlen.sell.dataobject.ImgsCategory;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * 商品详情
 */
@Data
public class ProductInfoVO  implements Serializable {

    private static final long serialVersionUID = 4426344472074116086L;
    @JsonProperty("id")
    private String productId;

    @JsonProperty("name")
    private  String productName;

    @JsonProperty("price")
    private BigDecimal productPrice;

    @JsonProperty("description")
    private String productDescription;

    /**
     * 品牌编号
     */
    @JsonProperty("brandType")
    private Integer brandType;

    @JsonProperty("icon")
    private  String productIcon;

    @JsonProperty("productImgs")
    private List<ImgsCategory> productImgsId;
}
