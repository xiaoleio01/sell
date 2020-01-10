package com.shawlen.sell.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.shawlen.sell.dataobject.OrderDetail;
import com.shawlen.sell.dto.OrderDTO;
import com.shawlen.sell.enums.ResultEnum;
import com.shawlen.sell.exception.SellException;
import com.shawlen.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ShawLen
 * @date 2019/11/4 4:09 下午
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO converter(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try {
            //String 类型的json转OrderDetail的list
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        }catch (Exception e){
            log.error("【对象转换】错误，String={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
