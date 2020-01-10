package com.shawlen.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author ShawLen
 * @date 2019/12/9 9:47 下午
 */
@Entity
@Data
@DynamicUpdate
public class ImgsCategory {

    @Id
    private Integer imgId;

    /** 图片地址 */
    private String imgPath;

    /** 图片类目：1详情，2参数，3首页滚动，4新品，5推荐，6其他 */
    private String imgCategory;

    /** 商品id */
    private String productId;

    /** 图片顺序，商品详情需要 */
    private String imgSeq;

    private String createTime;

    private String updateTime;
}
