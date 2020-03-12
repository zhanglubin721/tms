package com.zhanglubin.tms.dao;

import com.zhanglubin.tms.pojo.Student;
import org.apache.ibatis.annotations.Param;

public interface UserStudentDao {
    void deleteByStudentid(@Param("sid") Long sid);

    void insertUserStudent(@Param("user_username") String username,@Param("student_id") long sid);

    Student selectStudentByUsername(@Param("username") String username);
}
