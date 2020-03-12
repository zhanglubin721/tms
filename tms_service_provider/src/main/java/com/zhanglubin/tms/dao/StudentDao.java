package com.zhanglubin.tms.dao;

import com.github.pagehelper.Page;
import com.zhanglubin.tms.pojo.Student;
import org.apache.ibatis.annotations.Param;

public interface StudentDao {
    /**
     * 分页查询
     * @param queryString
     * @return Page<Student>
     */
    Page<Student> selectByConditon(@Param("queryString") String queryString);

    /**
     * 删除学生
     * @param sid
     */
    void deleteBySid(@Param("sid") Long sid);

    /**
     * 添加学生
     * @param student
     */
    void insertStudent(Student student);

    /**
     * 重置学生
     * @param sid
     */
    void updatePassword(@Param("sid") Long sid);

    /**
     * 根据学生id查找学生
     * @param sid
     */
    Student selectBySid(@Param("sid") Long sid);

    /**
     * 编辑学生信息
     * @param student
     */
    void updateStudent(Student student);
}
