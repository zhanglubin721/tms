package com.zhanglubin.tms.service;


import com.zhanglubin.tms.entity.PageResult;
import com.zhanglubin.tms.entity.QueryPageBean;
import com.zhanglubin.tms.pojo.Role;

import java.util.List;

public interface RoleService {
    PageResult findPage(QueryPageBean queryPageBean);

    Role findById(Integer id);

    List<Integer> findMenuIdsByRoleId(Integer id);

    List<Integer> findPermissionIdsByRoleId(Integer id);

    void add(Role role, Integer[] menuIds, Integer[] permissionIds);

    void edit(Role role, Integer[] menuIds, Integer[] permissionIds);

    List<Role> findAll();

    void delete(Integer id);

    Integer[] getTrueMenuIds(Integer[] menuIds);
}
