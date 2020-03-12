package com.zhanglubin.tms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhanglubin.tms.dao.*;
import com.zhanglubin.tms.entity.PageResult;
import com.zhanglubin.tms.entity.QueryPageBean;
import com.zhanglubin.tms.pojo.Course;
import com.zhanglubin.tms.pojo.Student;
import com.zhanglubin.tms.pojo.Teacher;
import com.zhanglubin.tms.pojo.User;
import com.zhanglubin.tms.service.CourseService;
import com.zhanglubin.tms.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherDao teacherDao;

    @Autowired
    private UserTeacherDao userTeacherDao;

    @Autowired
    private UserDao userDao;

    @Autowired
    private CourseTeacherDao courseTeacherDao;

    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public boolean isTeacher(String username) {
        Teacher teacher = userTeacherDao.selectTeacherByUsername(username);
        if (null != teacher) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        log.info("[教师信息-分页查询]data：{}",queryPageBean);
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        // 分页查询数据库
        Page<Student> page = teacherDao.selectByConditon(queryPageBean.getQueryString());
        //封装分页数据
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public Teacher findByTid(Long tid) {
        log.info("[根据id查询教师信息]tid:{}", tid);
        return teacherDao.findByTid(tid);
    }

    @Override
    public void edit(Teacher teacher) {
        log.info("[编辑教师信息]teacher:{}", teacher);
        teacherDao.updateTeacher(teacher);
    }

    @Override
    public void delete(Long tid) {
        log.info("[要删除的教师id]tid:{}", tid);
        courseTeacherDao.deleteByTeacherid(tid);
        userTeacherDao.deleteByTeacherid(tid);
        teacherDao.deleteByTeacherid(tid);
        String username = Long.toString(tid);
        User user = userDao.fndByUsername(username);

        int user_id = user.getId();
        userRoleDao.deleteByUserid(user_id);

        userDao.deleteByUsername(username);

    }

    @Override
    public void add(Teacher teacher) {
        log.info("[添加教师信息]teacher:{}", teacher);
        teacherDao.insertTeacher(teacher);
        User user = new User();
        String username = Long.toString(teacher.getTid());
        user.setUsername(username);
        String password = getBCryptPassword(teacher);
        user.setPassword(password);
        userDao.insertUser(user);

        User user1 = userDao.fndByUsername(user.getUsername());
        int user_id = user1.getId();
        userTeacherDao.insertUserTeacher(username, teacher.getTid());
        int role_id = 2;
        userRoleDao.insertUserRole(user_id, role_id);
    }

    private String getBCryptPassword(Teacher teacher) {
        String password = "123456";
        String hashpw = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashpw;
    }
}
