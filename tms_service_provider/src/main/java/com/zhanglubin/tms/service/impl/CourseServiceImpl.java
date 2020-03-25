package com.zhanglubin.tms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhanglubin.tms.dao.*;
import com.zhanglubin.tms.entity.PageResult;
import com.zhanglubin.tms.entity.QueryPageBean;
import com.zhanglubin.tms.pojo.Course;
import com.zhanglubin.tms.pojo.Student;
import com.zhanglubin.tms.pojo.User;
import com.zhanglubin.tms.service.CourseService;
import com.zhanglubin.tms.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

import javax.annotation.Resource;

@Slf4j
@Service
public class CourseServiceImpl implements CourseService {

    @Autowired
    private CourseDao courseDao;

    @Autowired
    private CourseStudentDao courseStudentDao;

    @Autowired
    private CourseTeacherDao courseTeacherDao;

    @Resource
    private AmqpTemplate amqpTemplate;

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        log.info("[课程信息-分页查询]data：{}",queryPageBean);
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        // 分页查询数据库
        Page<Course> page = courseDao.selectByConditon(queryPageBean.getQueryString());
        //封装分页数据
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public PageResult pageQueryByStudent(QueryPageBean queryPageBean, String username) {
        log.info("[课程信息-分页查询]data：{}",queryPageBean);
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        // 分页查询数据库
        Page<Course> page = courseDao.selectByConditonAndStudent(queryPageBean.getQueryString(), username);
        //封装分页数据
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public PageResult pageQueryByTeacher(QueryPageBean queryPageBean, String username) {
        log.info("[课程信息-分页查询]data：{}",queryPageBean);
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        // 分页查询数据库
        Page<Course> page = courseDao.selectByConditonAndTeacher(queryPageBean.getQueryString(), username);
        //封装分页数据
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Course findByCid(Long cid) {
        log.info("[根据id查询课程信息]cid:{}", cid);
        return courseDao.selectByid(cid);
    }

    @Override
    public void edit(Course course) {
        log.info("[编辑课程信息]course:{}", course);
        courseDao.updateCourse(course);
    }

    @Override
    public PageResult pageQueryBySpecialid(QueryPageBean queryPageBean, String specialId) {
        log.info("[选课-分页查询]data：{}",queryPageBean);
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        // 分页查询数据库
        Page<Course> page = courseDao.selectByConditonSpecialid(queryPageBean.getQueryString(), specialId);
        Page<Course> page1 = courseDao.selectByConditonRenxuan(queryPageBean.getQueryString());
        page.addAll(page1);
        //封装分页数据
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void chooseCourse(Long cid, Long sid) {
        courseStudentDao.insertStudentCourse(cid, sid);
    }

    @Override
    public void cancle(Long cid) {
        courseStudentDao.deleteByCourseid(cid);
    }

    @Override
    public void delete(Long cid) {
        log.info("[删除课程信息]cid:{}", cid);
        courseStudentDao.deleteByCourseid(cid);
        courseTeacherDao.deleteByCourseid(cid);
        courseDao.deleteByCourseid(cid);
    }

    @Override
    public void add(Course course) {
        courseDao.insertCourse(course);
        courseTeacherDao.insertCourse(course);
    }

    @Override
    public void testRabbitmq(Object message) {
        log.info("[测试rabbitmq]message:{}", message);
        amqpTemplate.convertAndSend("queueTestKey", message);
    }
}
