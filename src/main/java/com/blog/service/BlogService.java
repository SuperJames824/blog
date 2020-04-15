package com.blog.service;

import com.blog.entity.Blog;

import java.util.List;
import java.util.Map;

public interface BlogService {
    //无参数查询所有
    List<Blog> countList();
    //有参查询
    List<Blog> list(Map<String, Object> paramMap);
    //获取总数
    Long getTotal(Map<String, Object> paramMap);
    //根据id获取
    Blog findById(Integer paramInteger);
    //修改
    Integer update(Blog paramBlog);
    //获取最后一条
    Blog getLastBlog(Integer paramInteger);
    //获取下一条
    Blog getNextBlog(Integer paramInteger);
    //添加
    Integer add(Blog paramBlog);
    //删除
    Integer delete(Integer paramInteger);
    //按类型查询数量
    Integer getBlogCountByTypeId(Integer paramInteger);
}
