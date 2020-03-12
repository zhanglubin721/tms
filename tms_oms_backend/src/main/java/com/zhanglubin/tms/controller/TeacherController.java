package com.zhanglubin.tms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhanglubin.tms.common.MessageConst;
import com.zhanglubin.tms.entity.PageResult;
import com.zhanglubin.tms.entity.QueryPageBean;
import com.zhanglubin.tms.entity.Result;
import com.zhanglubin.tms.pojo.Student;
import com.zhanglubin.tms.pojo.Teacher;
import com.zhanglubin.tms.service.StudentService;
import com.zhanglubin.tms.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/teacher")
@Slf4j
public class TeacherController {

    @Reference
    private TeacherService teacherService;

    @GetMapping("/findPage")
    @PreAuthorize("hasAuthority('TEACHER_QUERY')")
    public PageResult findPage(QueryPageBean queryPageBean) {
        log.info("[教师信息-分页查询]data:{}", queryPageBean);
        try {
            return teacherService.pageQuery(queryPageBean);
        } catch (RuntimeException e) {
            log.error("[教师信息-分页查询]异常", e);
            return new PageResult(0L, Collections.emptyList());
        }
    }

    @GetMapping("/findByTid")
    @PreAuthorize("hasAuthority('TEACHER_QUERY')")
    public Result findByTid(Long tid) {
        log.info("[教师信息-根据教师id查询]data:{}", tid);
        try {
            Teacher teacher = teacherService.findByTid(tid);
            return new Result(true, MessageConst.QUERY_TEACHER_SUCCESS, teacher);
        } catch (RuntimeException e) {
            log.error("[教师信息-分页查询]异常", e);
            return new Result(false, MessageConst.QUERY_TEACHER_FAIL);
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('TEACHER_DELETE')")
    public Result delete(Long tid) {
        log.debug("[要删除的教师id]tid:{}", tid);
        try {
            teacherService.delete(tid);
            return new Result(true, MessageConst.DELETE_TEACHER_SUCCESS);
        } catch (RuntimeException e) {
            log.error("[学生信息-删除]异常", e);
            return new Result(false, MessageConst.DELETE_TEACHER_FAIL);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('TEACHER_ADD')")
    public Result add(@RequestBody Teacher teacher) {
        log.info("[教师信息-新增]data:{}", teacher);
        try {
            teacherService.add(teacher);
            return new Result(true, MessageConst.ADD_TEACHER_SUCCESS);
        } catch (RuntimeException e) {
            log.error("[教师信息-新增]", e);
            return new Result(false, MessageConst.ADD_TEACHER_FAIL + "：" + e.getMessage());
        }
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAnyAuthority('TEACHER_EDIT')")
    public Result edit(@RequestBody Teacher teacher) {
        log.debug("[修改教师信息]student:{}", teacher);
        try {
            teacherService.edit(teacher);
            return new Result(true, MessageConst.EDIT_TEACHER_SUCCESS);
        } catch (RuntimeException e) {
            log.error("[教师信息-编辑]异常", e);
            return new Result(false, MessageConst.EDIT_TEACHER_FAIL);
        }
    }
}
