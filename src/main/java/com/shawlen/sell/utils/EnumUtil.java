package com.shawlen.sell.utils;

import com.shawlen.sell.enums.CodeEnum;

/**
 * @author ShawLen
 * @date 2019/11/13 2:46 下午
 */
public class EnumUtil {

    /**
     * 通过枚举code获取枚举
     * @param code  枚举code
     * @param enumClass 要获取的枚举
     * @param <T>   <T extends CodeEnum> 意思就是该范型只能为继承CodeEnum的
     * @return
     */
    public static <T extends CodeEnum> T getByCode(Integer code,Class<T> enumClass){
        for(T each : enumClass.getEnumConstants()){
            //如果code相等的话，就返回这个枚举
            if (code.equals(each.getCode())) {
                return each;
            }
        }
        return null;
    }
}
