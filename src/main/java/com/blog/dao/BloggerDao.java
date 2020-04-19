package com.blog.dao;


import com.blog.entity.Blogger;




public interface BloggerDao {
    /**
     * 按姓名查找博主
     * @return
     */
    Blogger findByName(String username);

    /**
     * 查找全部
     * @return
     */
    Blogger findAll();

    /**
     * 更改博主信息
     * @param paramBlogger
     * @return
     */
     Integer update(Blogger paramBlogger);

    /**
     * 无参数查找唯一的blogger
     * * @return
     */
     Blogger find();


}
