package com.shawlen.sell.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @author ShawLen
 * @date 2019/11/19 3:45 下午
 */
@Data
@Entity
@DynamicUpdate
public class SellerInfo {

    @Id
    private  String sellerId;

    private  String username;

    private  String password;

    private  String openid;

}
