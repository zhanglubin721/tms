package com.zhanglubin.tms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.zhanglubin.tms.dao.StudentDao;
import com.zhanglubin.tms.dao.UserDao;
import com.zhanglubin.tms.dao.UserRoleDao;
import com.zhanglubin.tms.dao.UserStudentDao;
import com.zhanglubin.tms.entity.PageResult;
import com.zhanglubin.tms.entity.QueryPageBean;
import com.zhanglubin.tms.entity.Result;
import com.zhanglubin.tms.pojo.Student;
import com.zhanglubin.tms.pojo.User;
import com.zhanglubin.tms.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCrypt;

@Slf4j
@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentDao studentDao;
    @Autowired
    private UserDao userDao;
    @Autowired
    private UserStudentDao userStudentDao;
    @Autowired
    private UserRoleDao userRoleDao;

    @Override
    public PageResult pageQuery(QueryPageBean queryPageBean) {
        log.info("[学生信息-分页查询]data：{}",queryPageBean);
        //使用分页插件
        PageHelper.startPage(queryPageBean.getCurrentPage(),queryPageBean.getPageSize());
        // 分页查询数据库
        Page<Student> page = studentDao.selectByConditon(queryPageBean.getQueryString());
        //封装分页数据
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void delete(Long sid) {
        log.info("[要删除的学生id]sid:{}", sid);
        userStudentDao.deleteByStudentid(sid);
        studentDao.deleteBySid(sid);
        String userName = sid.toString();

        User user = userDao.fndByUsername(userName);
        int user_id = user.getId();
        userRoleDao.deleteByUserid(user_id);
        userDao.deleteByUsername(userName);

    }

    @Override
    public void add(Student student) {
        log.info("[添加学生信息]student:{}", student);
        studentDao.insertStudent(student);
        User user = new User();
        String username = Long.toString(student.getSid());
        user.setUsername(username);
        String password = getBCryptPassword(student);
        user.setPassword(password);
        userDao.insertUser(user);
        userStudentDao.insertUserStudent(username, student.getSid());
        User user1 = userDao.fndByUsername(username);
        int user_id = user1.getId();
        int role_id = 1;
        userRoleDao.insertUserRole(user_id, role_id);
    }

    @Override
    public void replace(Long sid) {
        log.info("[重置学生信息]sid:{}", sid);
        Student student = studentDao.selectBySid(sid);
        String password = getBCryptPassword(student);
        //进user表修改密码
        String username = Long.toString(student.getSid());
        userDao.updatePassword(username, password);
    }

    @Override
    public Student findBySid(Long sid) {
        log.info("[根据id查询学生信息]sid:{}", sid);
        return studentDao.selectBySid(sid);
    }

    @Override
    public void edit(Student student) {
        log.info("[编辑学生信息]student:{}", student);
        studentDao.updateStudent(student);
    }

    @Override
    public boolean isStudent(String username) {
        Student student = userStudentDao.selectStudentByUsername(username);
        if (null != student) {
            return true;
        } else {
            return false;
        }
    }

    private String getBCryptPassword(Student student) {
        //身份证取后六位得到初始化密码
        String idNumber = student.getIdNumber();
        StringBuilder stringBuilder = new StringBuilder(idNumber);
        StringBuilder delete = stringBuilder.delete(0, 12);
        String password = delete.toString();
        //通过加密得到密码
        String hashpw = BCrypt.hashpw(password, BCrypt.gensalt());
        return hashpw;
    }
}
