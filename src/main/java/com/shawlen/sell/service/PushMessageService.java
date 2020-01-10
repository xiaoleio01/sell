package com.shawlen.sell.service;

import com.shawlen.sell.dto.OrderDTO;

/**
 * 消息推送
 * @author ShawLen
 * @date 2019/11/20 2:06 下午
 */
public interface PushMessageService {

    /**
     * 订单状态变更消息
     * @param orderDTO
     */
    void orderStatus(OrderDTO orderDTO);

}
