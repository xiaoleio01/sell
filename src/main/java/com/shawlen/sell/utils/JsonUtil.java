package com.shawlen.sell.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * 将对照转化为json格式
 * @author ShawLen
 * @date 2019/11/12 9:59 上午
 */
public class JsonUtil {

    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }
}
