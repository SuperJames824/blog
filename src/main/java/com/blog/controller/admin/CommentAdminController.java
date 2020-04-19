package com.blog.controller.admin;


import com.blog.entity.Comment;
import com.blog.entity.PageBean;
import com.blog.service.CommentService;
import com.blog.util.JsonUtil;

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
@RequestMapping("/admin/comment")
public class CommentAdminController {

    @Autowired
    private CommentService commentService;

    @RequestMapping("/list")
    public String list(@RequestParam(value = "page", required = false) String page,
                       @RequestParam(value = "rows", required = false) String rows,
                       @RequestParam(value = "state", required=false) String state) throws Exception {
        PageBean pageBean = new PageBean(Integer.parseInt(page), Integer.parseInt(rows));
        Map<String,Object> map=new HashMap<>();
        map.put("start",pageBean.getStart());
        map.put("size",pageBean.getPageSize());
        map.put("state",state);

        //获取总数total
        Long total = commentService.getTotal(map);
        //获取rows
        List<Comment> list = commentService.list(map);

        Map<String, Object> result = new HashMap<>();
        result.put("total",total);
        result.put("rows",list);
        JsonUtil.getJson(result);
        return  null;

    }


    @RequestMapping({"/delete"})
    public String delete(@RequestParam("ids") String ids, HttpServletResponse response)
            throws Exception {
        String[] idsStr = ids.split(",");
        for (int i = 0; i < idsStr.length; i++) {
             this.commentService.delete(Integer.parseInt(idsStr[i]));
        }
        Map<String, Object> result = new HashMap<>();
        result.put("success", Boolean.TRUE);
        JsonUtil.getJson(result);
        return null;
    }

    @RequestMapping("/review")
    public String update(@RequestParam("ids") String ids, @RequestParam("state") Integer state, HttpServletResponse response) throws Exception {
        String[] idsStr=ids.split(",");
        for (int i = 0; i <idsStr.length ; i++) {
            Comment comment=new Comment();
            comment.setState(state);
            comment.setId(Integer.parseInt(idsStr[i]));
            commentService.update(comment);
        }

        Map<String, Object> result = new HashMap<>();
        result.put("success",Boolean.TRUE);
        JsonUtil.getJson(result);
        return null;
    }

}
