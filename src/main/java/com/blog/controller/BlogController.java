package com.blog.controller;


import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/blog")

public class BlogController {
    @Autowired
    private BlogService blogService;
    @Autowired
    private CommentService commentService;

    @RequestMapping("/articles/{id}")
    public ModelAndView details(@PathVariable("id") Integer id, HttpServletRequest request){
        ModelAndView mav=new ModelAndView();
        Blog blog=blogService.findById(id);

        //增加点击次数
        blog.setClickHit(blog.getClickHit()+1);
        blogService.update(blog);
        //上一篇下一篇
        Blog lastBlog=blogService.getLastBlog(id);
        Blog nextBlog = blogService.getNextBlog(id);
        String projectContext = request.getContextPath(); //获取虚拟目录:
        mav.addObject("pageCode",this.genUpAndDownPageCode(lastBlog,nextBlog,projectContext));

        //显示评论
        Map<String,Object> map=new HashMap<>();
        map.put("blogId",id);
        map.put("state",1);
        List<Comment> commentList = commentService.list(map);
        mav.addObject("commentList",commentList);

        mav.addObject("blog",blog);
        mav.addObject("mainPage","foreground/blog/view.jsp");
        mav.addObject("pageTitle","个人博客系统 > "+blog.getTitle());

        mav.setViewName("mainTemp");
        return  mav;
    }

    /**
     *
     * @param lastBlog 上一篇
     * @param nextBlog 下一篇
     * @param projectContext 项目名(虚拟目录)
     * @return
     */
    private String genUpAndDownPageCode(Blog lastBlog, Blog nextBlog, String projectContext)
    {
        StringBuffer pageCode = new StringBuffer();
        if ((lastBlog == null) || (lastBlog.getId() == null)) {
            pageCode.append("<p>上一篇：没有了</p>");
        } else {
            pageCode.append("<p>上一篇：<a href='" + projectContext + "/blog/articles/" + lastBlog.getId() + ".html'>" + lastBlog.getTitle() + "</a></p>");
        }
        if ((nextBlog == null) || (nextBlog.getId() == null)) {
            pageCode.append("<p>下一篇：没有了</p>");
        } else {
            pageCode.append("<p>下一篇：<a href='" + projectContext + "/blog/articles/" + nextBlog.getId() + ".html'>" + nextBlog.getTitle() + "</a></p>");
        }
        return pageCode.toString();
    }

}
