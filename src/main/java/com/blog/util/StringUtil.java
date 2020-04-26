package com.blog.util;

import java.util.ArrayList;
import java.util.List;


public class StringUtil {
    //判断是否为空  str.trim()去掉字符串首尾的空格并返回
    public static boolean isEmpty(String str) {
        if ((str == null) || ("".equals(str.trim()))) {
            return true;
        }
        return false;
    }

    public static boolean isNotEmpty(String str) {
        if ((str != null) && (!"".equals(str.trim()))) {
            return true;
        }
        return false;
    }


    /**供模糊查询使用，判断是否为空，然后加上'% %'
     * @param str
     * @return
     */

    public static String formatLike(String str) {
        if (isNotEmpty(str)) {
            return "%" + str + "%";
        }
        return null;
    }

    /**
     * 过滤空格方法
     * @param list
     * @return
     */
    public static List<String> filterWhite(List<String> list) {
        List<String> resultList = new ArrayList();
        for (String l : list) {//这一步把空格过滤掉了,因为空格也作为一个元素参加遍历
            if (isNotEmpty(l)) {
                resultList.add(l);
            }
        }
        return resultList;
    }
}


