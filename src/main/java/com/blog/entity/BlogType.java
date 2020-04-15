package com.blog.entity;

import java.io.Serializable;

public class BlogType implements Serializable {
    //主键
    private Integer id;
    //类型名称
    private String typeName;
    //此类型下博客数量
    private Integer blogCount;
    //序号
    private Integer OrderNo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public Integer getBlogCount() {
        return blogCount;
    }

    public void setBlogCount(Integer blogCount) {
        this.blogCount = blogCount;
    }

    public Integer getOrderNo() {
        return OrderNo;
    }

    public void setOrderNo(Integer orderNo) {
        OrderNo = orderNo;
    }
}

