package com.shawlen.sell.exception;

import com.shawlen.sell.enums.ResultEnum;
import lombok.Getter;

/**
 * @author ShawLen
 * @date 2019/10/31 7:58 下午
 */
@Getter
public class SellException extends RuntimeException {

    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMessage());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code,String message) {
        super(message);
        this.code = code;
    }
}
