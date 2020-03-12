package com.zhanglubin.tms.dao;

import com.zhanglubin.tms.pojo.Student;
import com.zhanglubin.tms.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

public interface UserTeacherDao {

    Teacher selectTeacherByUsername(String username);

    void insertUserTeacher(@Param("username") String username,@Param("tid") Long tid);

    void deleteByTeacherid(@Param("tid") Long tid);
}
