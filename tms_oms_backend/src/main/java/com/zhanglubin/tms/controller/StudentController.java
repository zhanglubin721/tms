package com.zhanglubin.tms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhanglubin.tms.entity.PageResult;
import com.zhanglubin.tms.entity.QueryPageBean;
import com.zhanglubin.tms.entity.Result;
import com.zhanglubin.tms.pojo.Student;
import com.zhanglubin.tms.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.access.prepost.PreAuthorize;
import com.zhanglubin.tms.common.MessageConst;
import java.util.Collections;

@RestController
@RequestMapping("/student")
@Slf4j
public class StudentController {

    @Reference
    private StudentService studentService;

    @GetMapping("/findPage")
    @PreAuthorize("hasAuthority('STUDENT_QUERY')")
    public PageResult findPage(QueryPageBean queryPageBean) {
        log.info("[学生信息-分页查询]data:{}", queryPageBean);
        try {
            return studentService.pageQuery(queryPageBean);
        } catch (RuntimeException e) {
            log.error("[学生信息-分页查询]异常", e);
            return new PageResult(0L, Collections.emptyList());
        }
    }

    @GetMapping("/findBySid")
    @PreAuthorize("hasAuthority('STUDENT_QUERY')")
    public Result findBySid(Long sid) {
        log.info("[学生信息-根据学生id查询]data:{}", sid);
        try {
            Student student = studentService.findBySid(sid);
            return new Result(true, MessageConst.QUERY_STUDENT_SUCCESS, student);
        } catch (RuntimeException e) {
            log.error("[学生信息-分页查询]异常", e);
            return new Result(false, MessageConst.QUERY_STUDENT_FAIL);
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('STUDENT_DELETE')")
    public Result delete(Long sid) {
        log.debug("[要删除的学生id]sid:{}", sid);
        try {
            studentService.delete(sid);
            return new Result(true, MessageConst.DELETE_STUDENT_SUCCESS);
        } catch (RuntimeException e) {
            log.error("[学生信息-删除]异常", e);
            return new Result(false, MessageConst.DELETE_STUDENT_FAIL);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('STUDENT_ADD')")
    public Result add(@RequestBody Student student) {
        log.info("[学生信息-新增]data:{}", student);
        try {
            studentService.add(student);
            return new Result(true, MessageConst.ADD_STUDENT_SUCCESS);
        } catch (RuntimeException e) {
            log.error("[学生信息-新增]", e);
            return new Result(false, MessageConst.ADD_STUDENT_FAIL + "：" + e.getMessage());
        }
    }

    @PutMapping("/replace")
    @PreAuthorize("hasAnyAuthority('STUDENT_REPLACE')")
    public Result replace(Long sid) {
        log.debug("[要重置的学生id]sid:{}", sid);
        try {
            studentService.replace(sid);
            return new Result(true, MessageConst.REPLACE_STUDENT_SUCCESS);
        } catch (RuntimeException e) {
            log.error("[学生信息-重置]异常", e);
            return new Result(false, MessageConst.REPLACE_STUDENT_FAIL);
        }
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAnyAuthority('STUDENT_EDIT')")
    public Result edit(@RequestBody Student student) {
        log.debug("[修改学生信息]student:{}", student);
        try {
            studentService.edit(student);
            return new Result(true, MessageConst.EDIT_STUDENT_SUCCESS);
        } catch (RuntimeException e) {
            log.error("[学生信息-重置]异常", e);
            return new Result(false, MessageConst.EDIT_STUDENT_FAIL);
        }
    }
}
