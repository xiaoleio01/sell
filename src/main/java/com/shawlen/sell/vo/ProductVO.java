package com.shawlen.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品（包含类目）
 */
@Data
public class ProductVO  implements Serializable {


    private static final long serialVersionUID = -2131028181232434433L;
    @JsonProperty("name")           //表示返回前端的名字为name,并不是显示categoryName
    private String categoryName;

    @JsonProperty("type")
    private Integer categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> ProductInfoVOList;
}
