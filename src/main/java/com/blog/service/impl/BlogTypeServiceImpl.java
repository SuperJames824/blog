package com.blog.service.impl;

import com.blog.dao.BlogTypeDao;
import com.blog.entity.BlogType;
import com.blog.service.BlogTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


@Service("blogTypeService")
public class BlogTypeServiceImpl implements BlogTypeService {

    @Resource
    private BlogTypeDao blogTypeDao;

    @Override
    public List<BlogType> findAll() {
        return blogTypeDao.findAll();
    }

    @Override
    public BlogType findById(Integer paramInteger) {
        return blogTypeDao.findById(paramInteger);
    }

    @Override
    public List<BlogType> findByMap(Map<String, Object> paramMap) {
        return blogTypeDao.findByMap(paramMap);
    }

    @Override
    public Long getTotal(Map<String, Object> paramMap) {
        return blogTypeDao.getTotal(paramMap);
    }

    @Override
    public Integer add(BlogType paramBlogType) {
        return blogTypeDao.add(paramBlogType);
    }

    @Override
    public Integer update(BlogType paramBlogType) {
        return blogTypeDao.update(paramBlogType);
    }

    @Override
    public Integer delete(Integer paramInteger) {
        return blogTypeDao.delete(paramInteger);
    }
}
