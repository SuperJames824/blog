package com.blog.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 重载方法
 */
public class DateUtil {

    //给一个参数调用这个
    public static String dateFormat(Date date){
        String format="";
        return  dateFormat(date,format);
    }


    //给两个参数调用这个
    //常见 yyyy-MM-dd HH:mm:ss
    public static String dateFormat(Date date,String paraFormat){
        String format;
        if ("".equals(paraFormat)||paraFormat==null){
            format="yyyy-MM-dd";
        }else{
            format=paraFormat;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(format);
        return sdf.format(date);
    }

}
