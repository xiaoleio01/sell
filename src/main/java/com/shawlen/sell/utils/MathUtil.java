package com.shawlen.sell.utils;


/**
 * @author ShawLen
 * @date 2019/11/13 9:15 上午
 */
public class MathUtil {

    private final static Double MONEY_RANGE = 0.01;

    /**
     * 判断2个金额是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equals(Double d1,Double d2){
        Double result = Math.abs(d1-d2);
        return result < MONEY_RANGE?true:false;
    }
}
