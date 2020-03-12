package com.zhanglubin.tms.dao;

import org.apache.ibatis.annotations.Param;

public interface CourseStudentDao {
    void deleteByCourseid(@Param("cid") Long cid);

    void insertStudentCourse(@Param("cid") Long cid, @Param("sid") Long sid);
}
