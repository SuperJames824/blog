package com.blog.controller.admin;

import com.blog.entity.Blog;
import com.blog.entity.PageBean;
import com.blog.service.BlogService;
import com.blog.util.JsonUtil;
import com.blog.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


//@RestController意思是在此类的所有方法前都加上@ResponseBody注解！！！
@RestController
@RequestMapping("/admin/blog")
public class BlogAdminController {
    @Autowired
    private BlogService blogService;

    @RequestMapping({"/save"})
    public String save( Blog blog)
            throws Exception {
        int resultTotal = 0;
        if (blog.getId() == null) {
            resultTotal = this.blogService.add(blog);
            // this.blogIndex.addIndex(blog);
        } else {
            resultTotal = this.blogService.update(blog);
            // this.blogIndex.updateIndex(blog);
        }
        Map<String,Object> map=new HashMap<>();
        if (resultTotal > 0) {
            map.put("success",Boolean.TRUE);
        } else {
            map.put("success",Boolean.FALSE);
        }
        return JsonUtil.getJson(map);
    }


    @RequestMapping({"/list"})
    public String list(@RequestParam(value = "page", required = false) String page,
                       @RequestParam(value = "rows", required = false) String rows,
                       Blog s_blog, HttpServletResponse response)
            throws Exception {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String, Object> map = new HashMap<>();
        map.put("title", StringUtil.formatLike(s_blog.getTitle()));
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        //分页查询博客信息列表
        List<Blog> blogList = this.blogService.list(map);
        //分页查询博客数量
        Long total = this.blogService.getTotal(map);
        Map<String,Object> result=new HashMap<>();
        result.put("rows",blogList);
        result.put("total",total);
        return JsonUtil.getJson(result);

    }

    @RequestMapping({"/findById"})
    public String findById(@RequestParam("id") String id)
            throws Exception {
        Blog blog = this.blogService.findById(Integer.parseInt(id));
        return JsonUtil.getJson(blog);
    }


    @RequestMapping({"/delete"})
    public String delete(@RequestParam("ids") String ids)
            throws Exception {
        String[] idsStr = ids.split(",");
        Map<String,Object> result=new HashMap<>();
        for (int i = 0; i < idsStr.length; i++) {
                this.blogService.delete(Integer.parseInt(idsStr[i]));
                result.put("success", Boolean.TRUE);
            }
           // this.blogIndex.deleteIndex(idsStr[i]);
           return JsonUtil.getJson(result);

        }



    }






