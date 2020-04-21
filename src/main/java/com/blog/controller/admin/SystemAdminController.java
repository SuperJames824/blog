package com.blog.controller.admin;


import com.blog.entity.Blog;
import com.blog.entity.BlogType;
import com.blog.entity.Blogger;
import com.blog.entity.Link;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.blog.service.BloggerService;
import com.blog.service.LinkService;
import com.blog.util.Const;
import com.blog.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 刷新系统缓存
 */
@RestController
@RequestMapping("/admin/system")
public class SystemAdminController {

    @Autowired
    private BlogTypeService blogTypeService;
    @Autowired
    private BloggerService bloggerService;
    @Autowired
    private LinkService linkService;
    @Autowired
    private BlogService blogService;

    @RequestMapping("/refreshSystem")
    public String refreshSystem(HttpServletRequest request) throws Exception {
        //拿到ServletContext对象application
        ServletContext application = request.getSession().getServletContext();

        List<BlogType> blogTypeCountList = blogTypeService.findAll();
        application.setAttribute(Const.BLOG_TYPE_COUNT_LIST,blogTypeCountList); //将公共参数抽取出来 命名为BLOG_TYPE_COUNT_LIST

        List<Blog> blogList = blogService.countList();
        application.setAttribute(Const.BLOG_TYPE_COUNT_LIST,blogList);

        List<Link> linkList = linkService.list(null);
        application.setAttribute(Const.Link_LIST,linkList);

        Blogger blogger = bloggerService.find();
        application.setAttribute("blogger",blogger);

        Map<String,Object> result=new HashMap<>();
        result.put("success",Boolean.TRUE);

        return  JsonUtil.getJson(result);

    }


}
