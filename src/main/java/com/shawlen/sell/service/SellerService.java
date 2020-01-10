package com.shawlen.sell.service;

import com.shawlen.sell.dataobject.SellerInfo;

/**
 * @author ShawLen
 * @date 2019/11/19 4:01 下午
 */
public interface SellerService {

    SellerInfo findSellerInfoByOpenid(String openid);
}
