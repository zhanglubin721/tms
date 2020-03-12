package com.zhanglubin.tms.dao;


import com.zhanglubin.tms.pojo.Course;
import org.apache.ibatis.annotations.Param;

public interface CourseTeacherDao {


    void deleteByCourseid(@Param("cid") Long cid);

    void insertCourse(Course course);

    void deleteByTeacherid(@Param("tid") Long tid);
}
