package com.blog.controller.admin;


import com.blog.entity.BlogType;
import com.blog.entity.PageBean;
import com.blog.service.BlogService;
import com.blog.service.BlogTypeService;
import com.blog.util.JsonUtil;
import com.blog.util.ResponseUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/blogType")
public class BlogTypeController {

    @Autowired
    private BlogTypeService blogTypeService;
    @Autowired
    private BlogService blogService;



    @RequestMapping({"/list"})
    public String list(@RequestParam(value = "page", required = false) String page, @RequestParam(value = "rows", required = false) String rows, HttpServletResponse response)
            throws Exception {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String, Object> map = new HashMap<>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<BlogType> blogTypeList = this.blogTypeService.findByMap(map);
        //这个map可以不传值
        Long total = this.blogTypeService.getTotal(map);

        Map<String, Object> result = new HashMap<>();
        result.put("rows", blogTypeList);
        result.put("total", total);

        return JsonUtil.getJson(result);
    }

    @RequestMapping({"/save"})
    public String save(BlogType blogType)
            throws Exception {
        int resultTotal = 0;
        if (blogType.getId() == null) {
            resultTotal = this.blogTypeService.add(blogType);
        } else {
            resultTotal = this.blogTypeService.update(blogType);
        }
        Map<String, Object> result = new HashMap<>();

        if (resultTotal > 0) {
            result.put("success", Boolean.TRUE);
        } else {
            result.put("success", Boolean.FALSE);
        }
        return JsonUtil.getJson(result);
    }


    @RequestMapping({"/delete"})
    public String delete(@RequestParam("ids") String ids)
            throws Exception {

        String[] idsStr = ids.split(",");
        Map<String, Object> result = new HashMap<>();
        for (int i = 0; i < idsStr.length; i++) {
            int blogCount = blogService.getBlogCountByTypeId(Integer.parseInt(idsStr[i]));
            if (blogCount > 0) {
                result.put("exist", Boolean.TRUE);
            } else {
                this.blogTypeService.delete(Integer.parseInt(idsStr[i]));
            }
        }
        result.put("success", Boolean.TRUE);
        return JsonUtil.getJson(result);
    }
}
