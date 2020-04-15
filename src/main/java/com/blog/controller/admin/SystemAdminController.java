package com.blog.controller.admin;

import com.blog.entity.BlogType;
import com.blog.service.BlogTypeService;
import com.blog.util.Const;
import com.blog.util.ResponseUtil;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 刷新系统缓存
 */
@Controller
@RequestMapping("/admin/system")
public class SystemAdminController {

    @Autowired
    private BlogTypeService blogTypeService;

    @RequestMapping("/refreshSystem")
    public String refreshSystem(HttpServletRequest request,HttpServletResponse response) throws Exception {
        //拿到ServletContext对象application
        ServletContext application = request.getSession().getServletContext();
        List<BlogType> blogTypeCountList = blogTypeService.findAll();
        //将公共参数抽取出来 命名为BLOG_TYPE_COUNT_LIST
        application.setAttribute(Const.BLOG_TYPE_COUNT_LIST,blogTypeCountList);

        JSONObject result=new JSONObject();
        result.put("success",Boolean.TRUE);

        ResponseUtil.write(response,result);
        return null;
    }


}
