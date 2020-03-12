package com.zhanglubin.tms.service;

import com.zhanglubin.tms.entity.PageResult;
import com.zhanglubin.tms.entity.QueryPageBean;
import com.zhanglubin.tms.entity.Result;
import com.zhanglubin.tms.pojo.Student;

public interface StudentService {
    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    void delete(Long sid);

    void add(Student student);

    void replace(Long sid);

    Student findBySid(Long sid);

    void edit(Student student);

    boolean isStudent(String username);
}
