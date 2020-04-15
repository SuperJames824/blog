package com.blog.service.impl;

import com.blog.dao.BloggerDao;
import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("bloggerService")
public class BloggerServiceImpl implements BloggerService {

@Resource
    private BloggerDao bloggerDao;

    @Override
    public Blogger findByName(String username) {
        return bloggerDao.findByName(username);
    }

    @Override
    public Blogger findAll() {
        return bloggerDao.findAll();
    }

    @Override
    public Integer update(Blogger paramBlogger) {
        return bloggerDao.update(paramBlogger);
    }
}

