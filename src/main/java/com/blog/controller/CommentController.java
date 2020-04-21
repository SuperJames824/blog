package com.blog.controller;


import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.blog.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;
    @Autowired
    private BlogService blogService;

    @RequestMapping("/save")
    @ResponseBody
    public String save(Comment comment, @RequestParam("imageCode") String imageCode, HttpServletRequest request) throws JsonProcessingException {
        String sRand = (String) request.getSession().getAttribute("sRand");

        Map<String, Object> result = new HashMap<>();
        int resultTotal = 0;
        //先验证验证码
        if (!sRand.equals(imageCode)) {
            result.put("success", Boolean.FALSE);
            result.put("errorInfo", "验证码填写错误!");
        } else {
            //获取本机ip地址
            String userIp = request.getRemoteAddr();
            System.out.println(userIp);
            comment.setUserIp(userIp);
            //如果评论是新增加的(因为不带id)
            if (comment.getId() == null) {
                resultTotal = commentService.add(comment);

                //评论后同时增加评论次数
                Blog blog = blogService.findById(comment.getBlog().getId());
                blog.setReplyHit(blog.getReplyHit()+1);
                blogService.update(blog);
            }
            if(resultTotal>0){
                result.put("success",Boolean.TRUE);
            }else {
                result.put("success",Boolean.FALSE);
            }
        }
        return JsonUtil.getJson(result);
    }
}