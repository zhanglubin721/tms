package com.zhanglubin.tms.service;

import com.zhanglubin.tms.entity.PageResult;
import com.zhanglubin.tms.entity.QueryPageBean;
import com.zhanglubin.tms.pojo.Teacher;

public interface TeacherService {


    /**
     * 查询是否是老师
     * @param username
     * @return
     */
    boolean isTeacher(String username);

    PageResult pageQuery(QueryPageBean queryPageBean);

    Teacher findByTid(Long tid);

    void delete(Long tid);

    void add(Teacher teacher);

    void edit(Teacher teacher);
}
