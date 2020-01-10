package com.shawlen.sell.repository;

import com.shawlen.sell.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author ShawLen
 * @date 2019/11/19 3:51 下午
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {


    SellerInfo findByOpenid(String openid);
}
