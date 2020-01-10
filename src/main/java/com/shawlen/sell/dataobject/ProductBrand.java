package com.shawlen.sell.dataobject;

import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
 * 商品品牌
 * @author ShawLen
 * @date 2019/12/2 5:07 下午
 */
public class ProductBrand implements Serializable {


    @Id
   private int brand_id;

    /** 品牌名 */
   private String brand_name;

    /** 品牌类目 */
   private int brand_type;

    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;
}
