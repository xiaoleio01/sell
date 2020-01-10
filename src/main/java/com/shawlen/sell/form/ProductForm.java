package com.shawlen.sell.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 商品修改提交表单实体
 * @author ShawLen
 * @date 2019/11/15 11:28 上午
 */
@Data
public class ProductForm {

    private String productId;

    /** 名字 */
    private String productName;

    /** 单价 */
    private BigDecimal productPrice;

    /** 库存 */
    private Integer productStock;

    /** 描述 */
    private String productDescription;

    /** 小图 */
    private String productIcon;

    /** 类目编号 */
    private Integer categoryType;

}
