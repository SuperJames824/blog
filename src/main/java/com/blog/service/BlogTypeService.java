package com.blog.service;

import com.blog.entity.BlogType;

import java.util.List;
import java.util.Map;

public interface BlogTypeService {

    //根据条件查询所有博客类型的list集合
    List<BlogType> findByMap(Map<String, Object> paramMap);
    //根据条件查询博客类型数量
    Long getTotal(Map<String, Object> paramMap);

    //添加
    Integer add(BlogType paramBlogType);
    //更改
    Integer update(BlogType paramBlogType);
    //删除
    Integer delete(Integer paramInteger);

    //根据id查询
    BlogType findById(Integer paramInteger);


    //无参数查询所有博客类型的list集合
    List<BlogType> findAll();
}
