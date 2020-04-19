package com.blog.service.impl;

import com.blog.dao.LinkDao;
import com.blog.entity.Link;
import com.blog.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("linkService")
public class LinkServiceImpl implements LinkService {

    @Autowired
    private LinkDao linkDao;


    @Override
    public int add(Link paramLink) {
        return linkDao.add(paramLink);
    }

    @Override
    public int update(Link paramLink) {
        return linkDao.update(paramLink);
    }

    @Override
    public List<Link> list(Map<String, Object> paramMap) {
        return linkDao.list(paramMap);
    }

    @Override
    public Long getTotal(Map<String, Object> paramMap) {
        return linkDao.getTotal(paramMap);
    }

    @Override
    public Integer delete(Integer paramInteger) {
        return linkDao.delete(paramInteger);
    }
}
