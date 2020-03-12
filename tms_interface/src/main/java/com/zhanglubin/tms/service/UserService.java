package com.zhanglubin.tms.service;

import com.zhanglubin.tms.pojo.User;

import java.util.List;

public interface UserService {

    /**
     * 用户登录
     * @param userName 用户名
     * @return User
     */
    User findByUsername(String userName);


    Integer findUserIdByUsername(String username);

    List<Integer> findRoleIdsByUserId(Integer id);
}
