package com.blog.util;

import java.util.ArrayList;
import java.util.List;


public class StringUtil {
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


    /**
     * @param str
     * 供模糊查询使用的，判断是否为空，然后给它加上 % %
     * @return
     */
    /* 24:   */
    public static String formatLike(String str)
    /* 25:   */ {
        if (isNotEmpty(str)) {
            return "%" + str + "%";
        }
        return null;
    }

    public static List<String> filterWhite(List<String> list) {
        List<String> resultList = new ArrayList();
        for (String l : list) {
            if (isNotEmpty(l)) {
                resultList.add(l);
            }
        }
        return resultList;
    }
}


