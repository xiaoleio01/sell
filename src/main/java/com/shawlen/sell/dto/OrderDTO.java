package com.shawlen.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.shawlen.sell.dataobject.OrderDetail;
import com.shawlen.sell.enums.OrderStatusEnum;
import com.shawlen.sell.enums.PatStatusEnum;
import com.shawlen.sell.utils.EnumUtil;
import com.shawlen.sell.utils.serializer.Date2LongSerializer;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Id;
import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author ShawLen
 * @date 2019/10/31 7:43 下午
 *
 * --@JsonInclude  此注释是返回的前端数据为null就不返回，也可以在此不写，在配置文件配置，
 */
@Data
@DynamicUpdate
@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /** 订单id */
    private String orderId;

    /** 买家姓名 */
    private String buyerName;

    /** 买家电话 */
    private String buyerPhone;

    /** 买家地址*/
    private String buyerAddress;

    /** 买家openId */
    private String buyerOpenid;

    /** 订单总额 */
    private BigDecimal orderAmount;

    /** 订单状态,默认0新下单 */
    private Integer orderStatus;

    /** 支付状态 默认0未支付*/
    private Integer payStatus ;

    /** 创建时间 */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;

    /** 更新时间 */
    //时间转long类型。
    @JsonSerialize(using = Date2LongSerializer.class)
    private  Date updateTime;

    @Transient
    private List<OrderDetail> orderDetailList;

    /**  通过订单状态CODE获取枚举   @JsonIgnore该注解是让下面方法对象转成JSON格式时忽略掉 */
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }

    /** 通过支付状态code获取支付枚举  */
    @JsonIgnore
    public PatStatusEnum getPatStatusEnum(){
        return EnumUtil.getByCode(payStatus,PatStatusEnum.class);
    }
}
