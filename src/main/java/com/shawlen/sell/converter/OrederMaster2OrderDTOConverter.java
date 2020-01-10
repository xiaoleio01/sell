package com.shawlen.sell.converter;

import com.shawlen.sell.dataobject.OrderMaster;
import com.shawlen.sell.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author ShawLen
 * @date 2019/11/4 10:28 上午
 */
public class OrederMaster2OrderDTOConverter {

    public static OrderDTO converter(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> converter(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(
                e -> converter(e)
        ).collect(Collectors.toList());
    }
}
