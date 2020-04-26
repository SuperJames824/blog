package com.blog.controller;


import com.blog.entity.Blog;
import com.blog.entity.Comment;
import com.blog.lucene.BlogIndex;
import com.blog.service.BlogService;
import com.blog.service.CommentService;
import com.blog.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
    @Autowired
    private BlogIndex blogIndex;

    @RequestMapping("/articles/{id}")
    public ModelAndView details(@PathVariable("id") Integer id, HttpServletRequest request){
        ModelAndView mav=new ModelAndView();
        Blog blog=blogService.findById(id);

        //增加点击次数
        blog.setClickHit(blog.getClickHit()+1);
        blogService.update(blog);
        //显示评论次数

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

        //显示关键字
        String keyWord=blog.getKeyWord();
        if (StringUtil.isEmpty(keyWord)){

        }
        mav.addObject("keyWords",keyWord);


        mav.addObject("blog",blog);
        mav.addObject("mainPage","foreground/blog/view.jsp");
        mav.addObject("pageTitle","个人博客系统 > "+blog.getTitle());

        mav.setViewName("mainTemp");
        return  mav;
    }


    /**
     * lucene按照关键字查询
     * @param q
     * @param page
     * @param request
     * @return
     * @throws Exception
     */
    @RequestMapping("/q")
    public ModelAndView search(@RequestParam(value = "q",required =false)String q,
                               @RequestParam(value = "page",required = false)String page,
                               HttpServletRequest request) throws Exception {
        if (StringUtil.isEmpty(page)){
            page="1";
        }

        ModelAndView mav=new ModelAndView();
        mav.addObject("mainPage","foreground/blog/result.jsp");
        //在lucence中查询

        List<Blog> blogList=blogIndex.searchBlog(q);
        int toIndex=Math.min(blogList.size(),Integer.parseInt(page)*10);
        mav.addObject("q",q);
        mav.addObject("blogList",blogList.subList((Integer.parseInt(page)-1)*10,toIndex));
        mav.addObject("resultTotal",blogList.size());
        mav.addObject("pagTitle","搜索关键字'"+q+"'结果页面_个人博客");
        mav.addObject("pageCode",genUpAndDownPageCode(Integer.parseInt(page),blogList.size(),q,10,request.getContextPath()));
        mav.setViewName("mainTemp");
        return  mav;

    }
    /**
     *  博客详情view.jsp中的上一篇 下一篇
     *
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

    /**
     * lucene查询结果翻页
     * @return
     */

    private String genUpAndDownPageCode(Integer page, Integer totalNum, String q, Integer pageSize, String projectContext)
    {
        long totalPage = totalNum % pageSize == 0 ? totalNum / pageSize : totalNum / pageSize + 1;
        StringBuffer pageCode = new StringBuffer();
        if (totalPage == 0L) {
            return "";
        }
        pageCode.append("<nav>");
        pageCode.append("<ul class='pager' >");
        if (page > 1) {
            pageCode.append("<li><a href='" + projectContext + "/blog/q.html?page=" + (page - 1) + "&q=" + q + "'>上一页</a></li>");
        } else {
            pageCode.append("<li class='disabled'><a href='#'>上一页</a></li>");
        }
        if (page < totalPage) {
            pageCode.append("<li><a href='" + projectContext + "/blog/q.html?page=" + (page + 1) + "&q=" + q + "'>下一页</a></li>");
        } else {
            pageCode.append("<li class='disabled'><a href='#'>下一页</a></li>");
        }
        pageCode.append("</ul>");
        pageCode.append("</nav>");

        return pageCode.toString();
    }





}
