package com.shawlen.sell.utils;

import java.util.Random;

/**
 * @author ShawLen
 * @date 2019/10/31 8:16 下午
 */
public class KeyUtil {

    /**
     * 生产唯一的主数
     * 格式：时间+随机叔
     * @return
     */
    public static synchronized String genUniqueKey(){
        Random random = new Random();
        Integer number= random.nextInt(900000) + 100000;
        return System.currentTimeMillis()+String.valueOf(number);
    }

}
