package com.blog.controller.admin;

import com.blog.entity.BlogType;
import com.blog.service.BlogTypeService;
import com.blog.util.Const;
import com.blog.util.JsonUtil;
import com.blog.util.ResponseUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 刷新系统缓存
 */
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {

    @Autowired
    private BlogTypeService blogTypeService;

    @RequestMapping("/refreshSystem")
    public String refreshSystem(HttpServletRequest request) throws Exception {
        //拿到ServletContext对象application
        ServletContext application = request.getSession().getServletContext();
        List<BlogType> blogTypeCountList = blogTypeService.findAll();
        //将公共参数抽取出来 命名为BLOG_TYPE_COUNT_LIST
        application.setAttribute(Const.BLOG_TYPE_COUNT_LIST,blogTypeCountList);

        Map<String,Object> result=new HashMap<>();
        result.put("success",Boolean.TRUE);

        JsonUtil.getJson(result);
        return null;
    }


}
