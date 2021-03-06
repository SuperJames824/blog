
package com.blog.service.impl;

import com.blog.entity.Blog;
import com.blog.entity.BlogType;
import com.blog.entity.Blogger;
//import com.blog.entity.Link;
import com.blog.entity.Link;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.blog.service.BloggerService;
//import com.blog.service.LinkService;
import com.blog.service.LinkService;
import com.blog.util.Const;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;



/**
 * 用于将部分数据提前从数据库中查出来存入application供其它页面直接调用，提升页面的性能
 * 该类实现了ApplicationContextAware接口，Spring容器初始化的时候会自动将applicationContext注入进来！
 * 实现了ServletContextListener接口会变成监听器
 */
@Component
public class InitComponent implements ServletContextListener, ApplicationContextAware {

    private static ApplicationContext applicationContext;
    /**
     * 自动注入ApplicationContext！！！！！！！
     */
    public void setApplicationContext(ApplicationContext ac)
            throws BeansException {
        applicationContext = ac;
    }

    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext application = servletContextEvent.getServletContext();



        BloggerService bloggerService = (BloggerService) applicationContext.getBean("bloggerService");
        Blogger blogger = bloggerService.findAll();//查询博主
        blogger.setPassword(null);//设置为空是为了安全,因为只是存到application中,不往数据库里面存
        application.setAttribute("blogger", blogger);

        BlogTypeService blogTypeService = (BlogTypeService) applicationContext.getBean("blogTypeService");
        List<BlogType> blogTypeCountList = blogTypeService.findAll();//无参数查询该博客类别下的所有博客数量,和博客
        application.setAttribute(Const.BLOG_TYPE_COUNT_LIST, blogTypeCountList);

        BlogService blogService = (BlogService)applicationContext.getBean("blogService");
        List<Blog> blogCountList = blogService.countList();//查询所有博客
        application.setAttribute(Const.BLOG_COUNT_LIST, blogCountList);

        LinkService linkService = (LinkService) applicationContext.getBean("linkService");
        List<Link> linkList = linkService.list(null);//查询友情链接
        application.setAttribute(Const.Link_LIST, linkList);
    }

    public void contextDestroyed(ServletContextEvent sce) {
    }
}


