package com.blog.controller.admin;
import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import com.blog.util.JsonUtil;
import com.blog.util.PasswordUtil;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

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
          //获取当前用户
          Subject currentUser= SecurityUtils.getSubject();

          //判断当前是否认证
          if(!currentUser.isAuthenticated()){
              //将前端传来的数据赋予Token
              UsernamePasswordToken token=new UsernamePasswordToken(blogger.getUserName(),blogger.getPassword());
              //记住我
              token.setRememberMe(true);
              try {
                  //认证登录
                  currentUser.login(token);
                  //用户名不存在
              }catch (UnknownAccountException uae){
                  request.setAttribute("errorInfo",uae.getMessage());
                  return "login";
                  //密码错误
              }catch (IncorrectCredentialsException ice ){
                  request.setAttribute("errorInfo","密码错误!!!");
                  return "login";
              }

          }

          return "redirect:/admin/main.jsp";

      }


    /**
     * 安全登出
     * @return
     */
    @RequestMapping("/loginOut")
      public  String loginOut(){
          Subject currentUser = SecurityUtils.getSubject();
          currentUser.logout();
          return "login";
      }


    /**
     * 修改密码
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping({"/modifyPassword"})
    public String modifyPassword(String newPassword)
            throws Exception
    {
        Blogger blogger = new Blogger();
        blogger.setPassword(PasswordUtil.getRealPwd(newPassword));
        int resultTotal = this.bloggerService.update(blogger);

        Map<String,Object> result=new HashMap<>();
        if (resultTotal > 0) {
            result.put("success", Boolean.TRUE);
        } else {
            result.put("success", Boolean.FALSE);
        }
        return JsonUtil.getJson(result);
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
     * 解决了前台富文本从session拿图片的问题
     * about me
     */
      @ResponseBody
      @RequestMapping("/find")
      public String find() throws JsonProcessingException {
          Blogger blogger = bloggerService.find();
          return  JsonUtil.getJson(blogger);
      }


   @RequestMapping({"/aboutMe"})
   public ModelAndView aboutMe() {
       ModelAndView mav = new ModelAndView();
       mav.addObject("blogger", this.bloggerService.find());
       mav.addObject("mainPage", "foreground/blogger/info.jsp");
       mav.addObject("pageTitle", "关于博主_Java开源博客系统");
       mav.setViewName("mainTemp");
       return mav;
   }
}

