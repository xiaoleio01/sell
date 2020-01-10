package com.shawlen.sell.dataobject;


import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

/**
 * ShawLen  20191028
 */
@Entity
@DynamicUpdate  //动态更新时间
@Data           //不用写get,set方法
public class ProductCategory {

    /** 类目Id */
    @Id
    @GeneratedValue
    private Integer categoryId;

    /** 类目名字 */
    private String categoryName;

    /** 类目编号*/
    private Integer categoryType;


    /** 创建时间 */
    private Date createTime;

    /** 修改时间 */
    private Date updateTime;

    public ProductCategory() {
    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }

    @Override
    public String toString() {
        return "ProductCategory{" +
                "categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                ", categoryType=" + categoryType +
                '}';
    }
}
