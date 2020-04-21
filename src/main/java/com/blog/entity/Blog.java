package com.blog.entity;


import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

 public class Blog implements Serializable {
    //主键
    private Integer id;
    //标题
    private String title;
    //摘要
    private String summary;
    //发表时间
    private Date releaseDate;
    //点击次数
    private Integer clickHit;
    //评论次数
    private Integer replyHit;
    //内容
    private String content;
    //纯文本内容
    private String contentNoTag;
    //博客数量
    private Integer blogCount;
    //方便操作，将Date类型转换成String,供前台的页面做按照日期展示博客
    private String releaseDateStr;
    //关键字
    private String keyWord;
     //博客类型
     private BlogType blogType;
    //图片列表
    private List<String> imagesList = new LinkedList();

     public Integer getId() {
         return id;
     }

     public void setId(Integer id) {
         this.id = id;
     }

     public String getTitle() {
         return title;
     }

     public void setTitle(String title) {
         this.title = title;
     }

     public String getSummary() {
         return summary;
     }

     public void setSummary(String summary) {
         this.summary = summary;
     }

     public Date getReleaseDate() {
         return releaseDate;
     }

     public void setReleaseDate(Date releaseDate) {
         this.releaseDate = releaseDate;
     }

     public Integer getClickHit() {
         return clickHit;
     }

     public void setClickHit(Integer clickHit) {
         this.clickHit = clickHit;
     }

     public Integer getReplyHit() {
         return replyHit;
     }

     public void setReplyHit(Integer replyHit) {
         this.replyHit = replyHit;
     }

     public String getContent() {
         return content;
     }

     public void setContent(String content) {
         this.content = content;
     }

     public String getContentNoTag() {
         return contentNoTag;
     }

     public void setContentNoTag(String contentNoTag) {
         this.contentNoTag = contentNoTag;
     }

     public BlogType getBlogType() {
         return blogType;
     }

     public void setBlogType(BlogType blogType) {
         this.blogType = blogType;
     }

     public Integer getBlogCount() {
         return blogCount;
     }

     public void setBlogCount(Integer blogCount) {
         this.blogCount = blogCount;
     }

     public String getReleaseDateStr() {
         return releaseDateStr;
     }

     public void setReleaseDateStr(String releaseDateStr) {
         this.releaseDateStr = releaseDateStr;
     }

     public String getKeyWord() {
         return keyWord;
     }

     public void setKeyWord(String keyWord) {
         this.keyWord = keyWord;
     }

     public List<String> getImagesList() {
         return imagesList;
     }

     public void setImagesList(List<String> imagesList) {
         this.imagesList = imagesList;
     }
 }
