package com.shawlen.sell.vo;

import lombok.Data;

import java.io.Serializable;

/**
 * 买家商品
 * @param <T>
 */
@Data
public class ResultVO<T> implements Serializable {

    private static final long serialVersionUID = 1088676560579937530L;

    /** 错误码 */
    private Integer code;

    /** 提示信息 */
    private String msg;

    /** 具体内容 */
    private T data;
}
