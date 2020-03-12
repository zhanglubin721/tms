package com.zhanglubin.tms.dao;

import com.zhanglubin.tms.pojo.Permission;
import org.apache.ibatis.annotations.Param;
import java.util.List;

/**
 * 权限DAO
 */
public interface PermissionDao {
    /**
     * 根据角色ID查询权限信息
     * @param roleId
     * @return
     */
    List<Permission> selectByRoleId(@Param("roleId") Integer roleId);

}
