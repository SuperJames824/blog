package com.blog.realm;

import com.blog.entity.Blogger;
import com.blog.service.BloggerService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class myRealm extends AuthorizingRealm {
    @Autowired
    private BloggerService bloggerService;


    /**
     认证方法
     1、该方法什么时候会被调用？
     Subject.login(token)
     2、方法的入参是什么？
     UsernamePasswordToken
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        //1.得到Token
        UsernamePasswordToken upToken= (UsernamePasswordToken) authenticationToken;
        //2.获取用户名,先做用户名判断
        String uname = upToken.getUsername();
        Blogger blogger = bloggerService.findByName(uname);
        if (blogger==null){
            throw new UnknownAccountException("用户名不存在");
        }else{
            //把用户信息存入session
            SecurityUtils.getSubject().getSession().setAttribute("currentUser",blogger);
        }
        //3.由shiro完成密码比较,已经完成用户名校验
        //先拿出数据库里面的正确密码
        Object principal=uname;
        Object credentials=blogger.getPassword();
        //realm名称
        String realmName=super.getName();
        //拿盐
        ByteSource salt = ByteSource.Util.bytes(uname);//生成盐时与用户名相关联
        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(principal,credentials,salt,realmName);
        return  info;

    }


    /**
     * 注册时用,,,放到数据库里面的加密密码
     * @param args
     */
    public static void main(String[] args) {
        Object principal="admin";
        Object credentials="123456";
        //拿盐
        ByteSource salt = ByteSource.Util.bytes(principal);//生成盐时与用户名相关联
        SimpleHash mima = new SimpleHash("MD5", credentials, salt, 1902);
        System.out.println(mima);
    }


    /**
     * 授权的方法,用不到
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }
}