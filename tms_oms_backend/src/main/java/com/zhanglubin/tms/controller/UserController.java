package com.zhanglubin.tms.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.zhanglubin.tms.common.MessageConst;
import com.zhanglubin.tms.entity.Result;
import com.zhanglubin.tms.pojo.Menu;
import com.zhanglubin.tms.service.MenuService;
import com.zhanglubin.tms.service.RoleService;
import com.zhanglubin.tms.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/user")
@Slf4j
public class UserController {

    @Reference
    private MenuService menuService;
    @Reference
    private UserService userService;
    @Reference
    private RoleService roleService;

    @RequestMapping("/loginSuccess")
    public Result loginSuccess() {
        return new Result(true, MessageConst.LOGIN_SUCCESS);
    }

    @RequestMapping("/loginFail")
    public Result loginFail() {
        return new Result(false, MessageConst.LOGIN_FAIL);
    }

    @GetMapping("/getUsername")
    public Result getUsername() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (null != principal && principal instanceof User) {
            User user = (User) principal;
            return new Result(true, MessageConst.GET_USERNAME_SUCCESS, user.getUsername());
        }
        return new Result(true, MessageConst.GET_USERNAME_FAIL);
    }

    @GetMapping("/getMenu")
    public Result getMenu() {
        try {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            if (null != principal && principal instanceof User) {
                User user = (User) principal;
                String username = user.getUsername();
                Integer userId = userService.findUserIdByUsername(username);
                List<Integer> roleIds = userService.findRoleIdsByUserId(userId);
                List<Integer> menuIdsAll = new ArrayList<>();
                for (Integer roleId : roleIds) {
                    List<Integer> menuIds = roleService.findMenuIdsByRoleId(roleId);
                    menuIdsAll.addAll(menuIds);
                }
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
                log.info("username:{}",username);
                log.info("userId:{}",userId);
                log.info("roleIds:{}",roleIds);
                log.info("menuIdsAll:{}",menuIdsAll);
                List<Integer> oneLeverMenuIds = new ArrayList<>();
                oneLeverMenuIds.addAll(menuService.findOnelevelById(menuIdsAll));
                List<Menu> menuList = menuService.findmenuInformation(oneLeverMenuIds,menuIdsAll);
                return new Result(true, MessageConst.GET_USERNAME_FAIL,menuList);
            }
        } catch (RuntimeException e) {
            log.error("",e);
        }
        return new Result(false, MessageConst.GET_USERNAME_FAIL);
    }
}
