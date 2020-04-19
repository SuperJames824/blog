package com.blog.controller.admin;

import com.blog.entity.Link;
import com.blog.entity.PageBean;
import com.blog.service.LinkService;
import com.blog.util.JsonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/link")
public class LinkAdminController {

    @Autowired
    private LinkService linkService;


    @RequestMapping({"/list"})
    public String list(@RequestParam(value = "page", required = false) String page,
                       @RequestParam(value = "rows", required = false) String rows,
                       HttpServletResponse response)
            throws Exception {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String, Object> map = new HashMap<>();
        map.put("start", pageBean.getStart());
        map.put("size", pageBean.getPageSize());
        List<Link> LinkList = this.linkService.list(map);
        //这个map可以不传值
        Long total = this.linkService.getTotal(map);

        Map<String, Object> result = new HashMap<>();
        result.put("rows", LinkList);
        result.put("total", total);

        return  JsonUtil.getJson(result);
    }

    @RequestMapping({"/save"})
    public String save(Link Link)
            throws Exception {
        int resultTotal = 0;
        if (Link.getId() == null) {
            resultTotal = this.linkService.add(Link);
        } else {
            resultTotal = this.linkService.update(Link);
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
                this.linkService.delete(Integer.parseInt(idsStr[i]));
        }
        result.put("success", Boolean.TRUE);
        return JsonUtil.getJson(result);
    }
}
