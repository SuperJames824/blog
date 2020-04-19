package com.blog.service;

import com.blog.entity.Link;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;



public interface LinkService {
    int add(Link paramLink);

    int update(Link paramLink);

    List<Link> list(Map<String, Object> paramMap);

    Long getTotal(Map<String, Object> paramMap);

    Integer delete(Integer paramInteger);
}
