package com.shawlen.sell.service;

import com.shawlen.sell.dto.OrderDTO;

/**
 * @author ShawLen
 * @date 2019/11/5 3:24 下午
 */
public interface BuyerService {

    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
