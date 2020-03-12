package com.zhanglubin.tms.service;

import com.zhanglubin.tms.entity.PageResult;
import com.zhanglubin.tms.entity.QueryPageBean;
import com.zhanglubin.tms.pojo.Course;

public interface CourseService {
    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    PageResult pageQuery(QueryPageBean queryPageBean);

    PageResult pageQueryByStudent(QueryPageBean queryPageBean, String username);

    PageResult pageQueryByTeacher(QueryPageBean queryPageBean, String username);

    Course findByCid(Long cid);

    void delete(Long cid);

    void add(Course course);

    void edit(Course course);

    PageResult pageQueryBySpecialid(QueryPageBean queryPageBean, String specialId);

    void chooseCourse(Long cid, Long sid);

    void cancle(Long cid);
}
