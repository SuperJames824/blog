package com.blog.util;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class PasswordUtil {


    /**
     * 传进来一个明文,通过md5加密成密文返回
     * @param credentials
     * @return
     */
    public  static String  getRealPwd(Object credentials) {
        //拿盐
        Object principal="admin";
        ByteSource salt = ByteSource.Util.bytes(principal);//生成盐时与用户名相关联
        return new SimpleHash("MD5", credentials, salt, 1902).toString();
    }


}
