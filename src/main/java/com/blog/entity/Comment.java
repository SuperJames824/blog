package com.blog.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Comment implements Serializable {

    private Integer id;
    //用户IP
    private String userIp;
    //评论内容
    private String Content;
    //博客
    private Blog blog;
    //发表日期
    private Date commentDate;
    //评论状态
    private Integer state;

}
