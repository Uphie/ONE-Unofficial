package com.uphie.one.utils;

import com.alibaba.fastjson.JSON;

import java.util.List;

/**
 * Created by Uphie on 2015/9/6.
 * Email: uphie7@gmail.com
 */
public class JsonUtil {
    /**
     * 将任意对象（包括数组、Map等数据结构）转换成json字符串
     * @param object
     * @return
     */
    public static String getJson(Object object){
        return JSON.toJSONString(object);
    }

    /**
     * 将Json解析成对象
     * @param json json字符串
     * @param clazz 对象类
     * @param <T>
     * @return
     */
    public static <T> T getEntity(String json, Class<T> clazz) {
        return JSON.parseObject(json, clazz);
    }

    /**
     * 将Json解析成对象数组
     * @param json json字符串
     * @param clazz 对象类
     * @param <T>
     * @return
     */
    public static <T> List<T> getEntities(String json,Class<T> clazz){
        return JSON.parseArray(json,clazz);
    }
}
