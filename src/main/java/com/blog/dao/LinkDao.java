package com.blog.dao;

import com.blog.entity.Link;

import java.util.List;
import java.util.Map;

  public interface LinkDao
{
    int add(Link paramLink);
  
    int update(Link paramLink);
  
    List<Link> list(Map<String, Object> paramMap);
  
    Long getTotal(Map<String, Object> paramMap);
  
    Integer delete(Integer paramInteger);
}


