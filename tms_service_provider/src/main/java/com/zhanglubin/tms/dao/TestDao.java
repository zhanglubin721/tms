package com.zhanglubin.tms.dao;

import com.zhanglubin.tms.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface TestDao {
    User testLogin(@Param("userName") String name);
}
