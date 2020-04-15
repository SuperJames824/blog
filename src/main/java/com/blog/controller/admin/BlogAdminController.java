package com.blog.controller.admin;

import com.blog.entity.Blog;
import com.blog.entity.PageBean;
import com.blog.service.BlogService;
import com.blog.util.ResponseUtil;
import com.blog.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin/blog")
public class BlogAdminController {
    @Autowired
    private BlogService blogService;

    @RequestMapping({"/save"})
    public String save(Blog blog, HttpServletResponse response)
            throws Exception {
        int resultTotal = 0;
        if (blog.getId() == null) {
            resultTotal = this.blogService.add(blog);
            // this.blogIndex.addIndex(blog);
        } else {
            resultTotal = this.blogService.update(blog).intValue();
            // this.blogIndex.updateIndex(blog);
        }

        JSONObject result = new JSONObject();
        if (resultTotal > 0) {
            result.put("success", Boolean.TRUE);
        } else {
            result.put("success", Boolean.FALSE);
        }
        ResponseUtil.write(response, result);
        return null;
    }


    @RequestMapping({"/list"})
    public String list(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows", required = false) String rows, Blog s_blog, HttpServletResponse response)
            throws Exception {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String, Object> map = new HashMap();
        map.put("title", StringUtil.formatLike(s_blog.getTitle()));
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        //分页查询博客信息列表
        List<Blog> blogList = this.blogService.list(map);
        //分页查询博客数量
        Long total = this.blogService.getTotal(map);
        JSONObject result = new JSONObject();
        //传递json数据
        JsonConfig jsonConfig = new JsonConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new DateJsonValueProcessor("yyyy-MM-dd"));
        JSONArray jsonArray = JSONArray.fromObject(blogList, jsonConfig);
        result.put("rows", jsonArray);//rows当前页数据量
        result.put("total", total);//total总共的数据
        ResponseUtil.write(response, result);
        return null;
    }

    @RequestMapping({"/findById"})
    public String findById(@RequestParam("id") String id, HttpServletResponse response)
            throws Exception {
        Blog blog = this.blogService.findById(Integer.parseInt(id));
        JSONObject jsonObject = JSONObject.fromObject(blog);
        ResponseUtil.write(response, jsonObject);
        return null;
    }


    @RequestMapping({"/delete"})
    public String delete(@RequestParam("ids") String ids, HttpServletResponse response)
            throws Exception {

        JSONObject result = new JSONObject();

        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
                this.blogService.delete(Integer.parseInt(idsStr[i]));
                result.put("success", Boolean.TRUE);
            }
           // this.blogIndex.deleteIndex(idsStr[i]);
            ResponseUtil.write(response, result);
            return null;
        }



    }






