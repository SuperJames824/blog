package com.blog.controller.admin;

import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import com.blog.util.JsonUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.aspectj.weaver.ast.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.UUID;

//博主相关
@Controller
@RequestMapping("/admin/blogger")
public class BloggerController {

   @Autowired
    private BloggerService bloggerService;

    private  HttpServletRequest request;

    /**假的
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
                  request.getSession().setAttribute("currentUser",blogger);
                  return "redirect:/admin/main.jsp";
              }
          }
          request.setAttribute("errorInfo","用户不存在");
          request.getSession().setAttribute("currentUser",blogger);
          return "login";
      }

   /**
     *更改个人信息
     * @param imageFile 头像文件
     * @param blogger
     * @param request
     * @return
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping("/save")
    public  String update(@RequestParam(value = "imageFile",required = false)MultipartFile imageFile ,
                          Blogger blogger,HttpServletRequest request) throws IOException {
        //存储博主个人简介图片
        if(!imageFile.isEmpty()){
            String filepath=request.getServletContext().getRealPath("/");
            System.out.println(filepath);   //D:\LearningSoft\IDEA 2019.3.3\ideawork\blog\target\blog\
            //uuid给文件生成随机名  imageFile.getOriginalFilename()获取文件本来名然后取后缀
            String imageName=UUID.randomUUID().toString().substring(0,6).replace("-", "")+"."+imageFile.getOriginalFilename().split("\\.")[1];
            //使用 MulitpartFile 接口中方法，把上传的文件写到指定位置
            imageFile.transferTo(new File(filepath+"static/userImages/"+imageName));
            //更改blogger的图片文件名称
            blogger.setImageName(imageName);
        }




        //数据库更新之前,先把session中的currentUser的信息更新一下！！
        request.getSession().setAttribute("currentUser",blogger);
        int resultTotal = this.bloggerService.update(blogger);

        //后端判读并且传过去提示信息
        StringBuffer result=new StringBuffer();
        if(resultTotal>0){
            result.append("<script language='javascript'>alert('修改成功！'); </script>");
        }else {
            result.append("<script language='javascript'>alert('修改失败！'); </script>") ;
        }
        return JsonUtil.getJson(result);
    }

    /**
     * 为了解决前台富文本从session拿图片的问题
     * @return
     * @throws JsonProcessingException
     */
      @ResponseBody
      @RequestMapping("/find")
      public String find() throws JsonProcessingException {
          Blogger blogger = bloggerService.find();
          return  JsonUtil.getJson(blogger);
      }

}