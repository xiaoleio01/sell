package com.shawlen.sell.service.impl;

import com.shawlen.sell.dataobject.SellerInfo;
import com.shawlen.sell.repository.SellerInfoRepository;
import com.shawlen.sell.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author ShawLen
 * @date 2019/11/19 4:02 下午
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository sellerInfoRepository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return sellerInfoRepository.findByOpenid(openid);
    }
}
