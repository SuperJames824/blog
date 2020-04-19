package com.blog.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.text.SimpleDateFormat;

/**
 * 用mapper解析对象为json，并且设置时间格式，
 */
public class JsonUtil {

    public static String getJson(Object object) throws JsonProcessingException {
        return getJson(object,"yyyy-MM-dd");
    }




    //重载方法
    public static String getJson(Object object,String df) throws JsonProcessingException {
        ObjectMapper mapper=new ObjectMapper();
        //设置mapper内的时间格式不为时间戳样式
        mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS,false);
        //设置mapper内的时间格式
        SimpleDateFormat sdf = new SimpleDateFormat(df);
        mapper.setDateFormat(sdf);
        //mapper解析对象
        return  mapper.writeValueAsString(object);

    }

}
