package com.blog.service.impl;

import com.blog.dao.CommentDao;
import com.blog.entity.Comment;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service("commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;
    @Override
    public int add(Comment paramComment) {
        return commentDao.add(paramComment);
    }

    @Override
    public int update(Comment paramComment) {
        return commentDao.update(paramComment);
    }

    @Override
    public List<Comment> list(Map<String, Object> paramMap) {
        return commentDao.list(paramMap);
    }

    @Override
    public Long getTotal(Map<String, Object> paramMap) {
        return commentDao.getTotal(paramMap);
    }

    @Override
    public Integer delete(Integer paramInteger) {
        return commentDao.delete(paramInteger);
    }
}
