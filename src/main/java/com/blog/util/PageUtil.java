package com.blog.util;



public class PageUtil {


    /**         点击不同按钮跳转不同也的实现 主要由 url带的参数当前页page决定,
     *          所以就在不停地判断然后改变"page"

     * @param targetUrl 目标url即为request.getContextPath() + "/index.html"
     * @param totalNum          总数
     * @param currentPage       当前页
     * @param pageSize          当前页容量
     * @param param          releaseDateStr发布日期 和 typeId博客类型
     * @return
     */

    public static String genPagination(String targetUrl, long totalNum, int currentPage, int pageSize, String param) {
        long totalPage = totalNum % pageSize == 0L ? totalNum / pageSize : totalNum / pageSize + 1L;
        if (totalPage == 0L) {
            return "未查询到数据";
        }
        StringBuffer pageCode = new StringBuffer();
        pageCode.append("<li><a href='" + targetUrl + "?page=1&" + param + "'>首页</a></li>");
        if (currentPage > 1) {//不是第一页,则出现第一页按钮
            pageCode.append("<li><a href='" + targetUrl + "?page=" + (currentPage - 1) + "&" + param + "'>上一页</a></li>");
        } else {//是第一页,class='disabled'不让点第一页按钮
            pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }
        //展示出所有页的按钮
        for (int i = currentPage - 2; i <= currentPage + 2; i++) {
            if ((i >= 1) && (i <= totalPage)) {
                if (i == currentPage) {
                    //如果是当前页,使它class属性变成active,即按钮变蓝色
                    pageCode.append("<li class='active'><a href='" + targetUrl + "?page=" + i + "&" + param + "'>" + i + "</a></li>");
                } else {//不是当前页,则默认样式
                    pageCode.append("<li><a href='" + targetUrl + "?page=" + i + "&" + param + "'>" + i + "</a></li>");
                }
            }
        }
        if (currentPage < totalPage) {
            pageCode.append("<li><a href='" + targetUrl + "?page=" + (currentPage + 1) + "&" + param + "'>下一页</a></li>");
        } else {
            pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
        }
        pageCode.append("<li><a href='" + targetUrl + "?page=" + totalPage + "&" + param + "'>尾页</a></li>");
        return pageCode.toString();
    }
}



