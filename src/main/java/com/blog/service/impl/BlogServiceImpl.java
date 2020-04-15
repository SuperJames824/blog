package com.blog.service.impl;

import com.blog.dao.BlogDao;
import com.blog.entity.Blog;
import com.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Service("blogService")
public class BlogServiceImpl implements BlogService {

    @Resource
    private BlogDao blogDao;

    @Override
    public List<Blog> countList() {
        return blogDao.countList();
    }

    @Override
    public List<Blog> list(Map<String, Object> paramMap) {
        return blogDao.list(paramMap);
    }

    @Override
    public Long getTotal(Map<String, Object> paramMap) {
        return blogDao.getTotal(paramMap);
    }

    @Override
    public Blog findById(Integer paramInteger) {
        return blogDao.findById(paramInteger);
    }

    @Override
    public Integer update(Blog paramBlog) {
        return blogDao.update(paramBlog);
    }

    @Override
    public Blog getLastBlog(Integer paramInteger) {
        return blogDao.getLastBlog(paramInteger);
    }

    @Override
    public Blog getNextBlog(Integer paramInteger) {
        return blogDao.getNextBlog(paramInteger);
    }

    @Override
    public Integer add(Blog paramBlog) {
        return blogDao.add(paramBlog);
    }

    @Override
    public Integer delete(Integer paramInteger) {
        return blogDao.delete(paramInteger);
    }

    @Override
    public Integer getBlogCountByTypeId(Integer paramInteger) {
        return blogDao.getBlogCountByTypeId(paramInteger);
    }

}
