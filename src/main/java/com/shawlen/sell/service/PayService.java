package com.shawlen.sell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.shawlen.sell.dto.OrderDTO;

/**
 * @author ShawLen
 * @date 2019/11/7 2:39 下午
 */
public interface PayService {

    /**
     * 创建支付
     * @param orderDTO
     * @return
     */
    PayResponse create(OrderDTO orderDTO);

    /**
     * 异步通知
     * @param notifyData
     * @return
     */
    PayResponse notify(String notifyData);

    /**
     * 微信退款
     * @param orderDTO
     */
    RefundResponse refund(OrderDTO orderDTO);
}
