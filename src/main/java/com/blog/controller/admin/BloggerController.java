package com.blog.controller.admin;

import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

//博主相关
@Controller
@RequestMapping("/blogger")
public class BloggerController {

   @Autowired
    private BloggerService bloggerService;
    /**
     * 验证登录功能
     * @return
     */

      @RequestMapping({"/login"})
      public String login(Blogger blogger, HttpServletRequest request)
      {
          String userName = blogger.getUserName();
          Blogger b = bloggerService.findByName(userName);
          if(b!=null){
              if (b.getPassword().equals(blogger.getPassword())){
                  return "redirect:/admin/main.jsp";
              }
          }
          request.setAttribute("errorInfo","用户不存在");
          return "login";
      }
}