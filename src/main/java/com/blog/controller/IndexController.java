package com.blog.controller;

import com.blog.entity.Blog;
import com.blog.entity.PageBean;
import com.blog.service.BlogService;
import com.blog.util.JsonUtil;
import com.blog.util.PageUtil;
import com.blog.util.StringUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 首页
 */
@Controller
public class IndexController {

    @Autowired
    private BlogService blogService;

    @RequestMapping("/index")
    public ModelAndView index(@RequestParam(value="page", required=false) String page,
                              @RequestParam(value = "rows", required = false) String rows,
                              @RequestParam(value = "typeId",required = false)String typeId,
                              @RequestParam(value = "releaseDateStr",required = false)String releaseDateStr,
                              HttpServletRequest request) throws JsonProcessingException {
        ModelAndView mav=new ModelAndView();
        mav.addObject("pageTitle","个人博客系统");

        if (StringUtil.isEmpty(page)) {
            page = "1";
        }
        PageBean pageBean = new PageBean(Integer.parseInt(page), 10);
        Map<String, Object> map = new HashMap<>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        map.put("typeId",typeId);
        map.put("releaseDateStr",releaseDateStr);

        //按条件查询博客,  把查出来的blog列表放到list.jsp中
        List<Blog> list = blogService.list(map);
        mav.addObject("blogList",list);
        mav.addObject("mainPage","foreground/blog/list.jsp");

        //调用工具类进行分页操作
        StringBuffer param = new StringBuffer();
        if (StringUtil.isNotEmpty(typeId)) {
            param.append("typeId=" + typeId + "&");
        }
        if (StringUtil.isNotEmpty(releaseDateStr)) {
            param.append("releaseDateStr=" + releaseDateStr + "&");
        }
        //jsp页面的引用 pageCode翻页
        mav.addObject("pageCode", PageUtil.genPagination(request.getContextPath() + "/index.html",
                blogService.getTotal(map), Integer.parseInt(page), 10, param.toString()));

        mav.setViewName("mainTemp");
        return mav;
    }
}
