package com.shawlen.sell.enums;

import lombok.Getter;

/**
 * @author ShawLen
 * @date 2019/10/31 2:17 下午
 */
@Getter
public enum PatStatusEnum implements CodeEnum{
    WAIT(0,"未支付"),
    SUCCESS(1,"支付成功")
    ;

    private Integer code;

    private String message;

    PatStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
