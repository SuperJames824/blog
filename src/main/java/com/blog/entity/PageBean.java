package com.blog.entity;

public class PageBean {
    private int page;
    private int pageSize;
    private int start;

    public PageBean(int page, int pageSize) {
        this.page = page;
        this.pageSize = pageSize;
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPageSize() {
        return this.pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getStart() {
        return (this.page -1) * this.pageSize;
    }
}
