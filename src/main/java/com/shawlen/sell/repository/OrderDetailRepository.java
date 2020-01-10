package com.shawlen.sell.repository;

import com.shawlen.sell.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * @author ShawLen
 * @date 2019/10/31 2:46 下午
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    List<OrderDetail> findByOrderId(String orderId);
}
