package com.zhanglubin.tms.dao;

import com.github.pagehelper.Page;
import com.zhanglubin.tms.pojo.Course;
import com.zhanglubin.tms.pojo.Student;
import com.zhanglubin.tms.pojo.Teacher;
import org.apache.ibatis.annotations.Param;

public interface TeacherDao {

    /**
     * 分页查询
     * @param queryString
     * @return Page<Student>
     */
    Page<Student> selectByConditon(@Param("queryString") String queryString);

    /**
     * 根据id查询
     * @param tid
     * @return Teacher
     */
    Teacher findByTid(@Param("tid") Long tid);

    /**
     * 编辑教师信息
     * @param teacher
     * @return
     */
    void updateTeacher(Teacher teacher);

    /**
     * 新增教师
     * @param teacher
     * @return
     */
    void insertTeacher(Teacher teacher);

    /**
     * 删除教师
     * @param tid
     * @return
     */
    void deleteByTeacherid(@Param("tid") Long tid);
}
