package com.zhanglubin.tms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhanglubin.tms.common.MessageConst;
import com.zhanglubin.tms.entity.PageResult;
import com.zhanglubin.tms.entity.QueryPageBean;
import com.zhanglubin.tms.entity.Result;
import com.zhanglubin.tms.pojo.Course;
import com.zhanglubin.tms.pojo.Student;
import com.zhanglubin.tms.service.CourseService;
import com.zhanglubin.tms.service.StudentService;
import com.zhanglubin.tms.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/course")
@Slf4j
public class CourseController {

    @Reference
    private CourseService courseService;

    @Reference
    private StudentService studentService;

    @Reference
    private TeacherService teacherService;

    @GetMapping("/findPage")
    @PreAuthorize("hasAuthority('COURSE_QUERY')")
    public PageResult findPage(QueryPageBean queryPageBean) {
        log.info("[课程信息-分页查询]data:{}", queryPageBean);
        String username = getUsername();
        log.info("[获取用户名]username:{}", username);
        //判断当前用户是不是学生
        if (studentService.isStudent(username)) {
            //如果是,根据学生sid去查该学生所选的课
            return courseService.pageQueryByStudent(queryPageBean,username);
        } else if (teacherService.isTeacher(username)) { //判断当前用户是不是老师
            //如果是,根据老师tid去查该老师所教的课
            return courseService.pageQueryByTeacher(queryPageBean,username);
        } else {
            //如果既不是学生也不是老师,就展示所有课程
            try {
                return courseService.pageQuery(queryPageBean);//查询所有课程
            } catch (RuntimeException e) {
                log.error("[课程信息-分页查询]异常", e);
                return new PageResult(0L, Collections.emptyList());
            }
        }

    }

    @GetMapping("/chooseFindPage")
    @PreAuthorize("hasAuthority('COURSE_CHOOSE')")
    public PageResult chooseFindPage(QueryPageBean queryPageBean) {
        log.info("[选课-分页查询]data:{}", queryPageBean);
        String username = getUsername();
        long sid = Long.parseLong(username);
        log.info("[获取学生id]sid:{}", sid);
        Student student = studentService.findBySid(sid);
        StringBuffer sb = new StringBuffer(student.getClass_id());
        String specialId = sb.substring(0, 1);
        return courseService.pageQueryBySpecialid(queryPageBean,specialId);
    }

    @GetMapping("/findByCid")
    @PreAuthorize("hasAuthority('COURSE_QUERY')")
    public Result findBySid(Long cid) {
        log.info("[课程信息-根据课程id查询]data:{}", cid);
        try {
            Course course = courseService.findByCid(cid);
            return new Result(true, MessageConst.QUERY_COURSE_SUCCESS, course);
        } catch (RuntimeException e) {
            log.error("[课程信息-分页查询]异常", e);
            return new Result(false, MessageConst.QUERY_COURSE_FAIL);
        }
    }

    @DeleteMapping("/delete")
    @PreAuthorize("hasAuthority('COURSE_DELETE')")
    public Result delete(Long cid) {
        log.debug("[要删除的课程id]cid:{}", cid);
        try {
            courseService.delete(cid);
            return new Result(true, MessageConst.DELETE_COURSE_SUCCESS);
        } catch (RuntimeException e) {
            log.error("[课程信息-删除]异常", e);
            return new Result(false, MessageConst.DELETE_COURSE_FAIL);
        }
    }

    @PostMapping("/add")
    @PreAuthorize("hasAuthority('COURSE_ADD')")
    public Result add(@RequestBody Course course) {
        log.info("[课程信息-新增]data:{}", course);
        try {
            courseService.add(course);
            return new Result(true, MessageConst.ADD_COURSE_SUCCESS);
        } catch (RuntimeException e) {
            log.error("[课程信息-新增]", e);
            return new Result(false, MessageConst.ADD_COURSE_FAIL + "：" + e.getMessage());
        }
    }

    @PutMapping("/edit")
    @PreAuthorize("hasAnyAuthority('COURSE_EDIT')")
    public Result edit(@RequestBody Course course) {
        log.debug("[修改课程信息]student:{}", course);
        try {
            courseService.edit(course);
            return new Result(true, MessageConst.EDIT_COURSE_SUCCESS);
        } catch (RuntimeException e) {
            log.error("[课程信息-重置]异常", e);
            return new Result(false, MessageConst.EDIT_COURSE_FAIL);
        }
    }

    @PostMapping("/chooseByCid")
    @PreAuthorize("hasAuthority('COURSE_CHOOSE')")
    public Result chooseByCid(Long cid) {
        log.info("[选课]cid:{}", cid);
        try {
            long sid = Long.parseLong(getUsername());
            courseService.chooseCourse(cid, sid);
            return new Result(true, MessageConst.CHOOSE_COURSE_SUCCESS);
        } catch (RuntimeException e) {
            log.error("[选课]", e);
            return new Result(false, MessageConst.CHOOSE_COURSE_FAIL + "：" + e.getMessage());
        }
    }

    @DeleteMapping("/cancle")
    @PreAuthorize("hasAuthority('COURSE_CHOOSE')")
    public Result cancle(Long cid) {
        log.debug("[取消选课]cid:{}", cid);
        try {
            courseService.cancle(cid);
            return new Result(true, MessageConst.CANCLE_COURSE_SUCCESS);
        } catch (RuntimeException e) {
            log.error("[取消选课]异常", e);
            return new Result(false, MessageConst.CANCLE_COURSE_FAIL);
        }
    }

    public String  getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (null != principal && principal instanceof User) {
            User user = (User) principal;
            return user.getUsername();
        }
        return null;
    }
}
