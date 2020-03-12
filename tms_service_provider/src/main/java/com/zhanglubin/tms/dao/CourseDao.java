package com.zhanglubin.tms.dao;

import com.github.pagehelper.Page;
import com.zhanglubin.tms.pojo.Course;
import com.zhanglubin.tms.pojo.Student;
import org.apache.ibatis.annotations.Param;

public interface CourseDao {
    /**
     * 分页查询
     * @param queryString
     * @return Page<Student>
     */
    Page<Course> selectByConditon(@Param("queryString") String queryString);

    Page<Course> selectByConditonAndStudent(@Param("queryString") String queryString, @Param("username") String username);

    Page<Course> selectByConditonAndTeacher(@Param("queryString") String queryString, @Param("username") String username);

    Course selectByid(@Param("cid") Long cid);

    void updateCourse(Course course);

    void insertCourse(Course course);

    void deleteByCourseid(@Param("cid") Long cid);

    Page<Course> selectByConditonSpecialid(@Param("queryString") String queryString,@Param("specialId") String specialId);

    Page<Course> selectByConditonRenxuan(@Param("queryString") String queryString);

}
