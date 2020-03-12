package com.zhanglubin.tms.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.zhanglubin.tms.dao.PermissionDao;
import com.zhanglubin.tms.dao.RoleDao;
import com.zhanglubin.tms.dao.UserDao;
import com.zhanglubin.tms.pojo.Permission;
import com.zhanglubin.tms.pojo.Role;
import com.zhanglubin.tms.pojo.User;
import com.zhanglubin.tms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Slf4j
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private RoleDao roleDao;
    @Autowired
    private PermissionDao permissionDao;


    @Override
    public User findByUsername(String userName) {

        log.info("[登录]username：{}",userName);
        User user = userDao.fndByUsername(userName);

        if (null == user) {
            return null;
        }
        //根据用户ID查角色信息
        List<Role> roles = roleDao.selectByUserId(user.getId());
        for (Role role : roles) {
            //根据角色ID查权限信息
            List<Permission> permissions = permissionDao.selectByRoleId(role.getId());
            role.getPermissions().addAll(permissions);
        }
        user.getRoles().addAll(roles);
        return user;
    }

    @Override
    public List<Integer> findRoleIdsByUserId(Integer id) {
        log.info("[用户-根据用户ID查询所有角色ID]data: {}",id);
        return userDao.findRoleIdsByUserId(id);
    }

    public Integer findUserIdByUsername(String username) {
        Integer userId = null;
        try {
            log.info("[用户-根据用户名查询用户ID]username：{}",username);
            userId = userDao.selectUserIdByUsername(username);
        } catch (RuntimeException e) {
            log.error("",e);
        }
        return userId;
    }
}
