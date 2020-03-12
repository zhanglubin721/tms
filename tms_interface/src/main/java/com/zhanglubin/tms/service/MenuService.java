package com.zhanglubin.tms.service;


import com.zhanglubin.tms.entity.PageResult;
import com.zhanglubin.tms.entity.QueryPageBean;
import com.zhanglubin.tms.pojo.Menu;

import java.util.List;

public interface MenuService {
    void add(Menu menu);

    PageResult pageQuery(QueryPageBean queryPageBean);

    void delete(Integer id);

    Menu findById(Integer id);

    void edit(Menu menu);

    List<Menu> findAll();

    List<Integer> findOnelevelById(List<Integer> menuIdsAll);

    List<Menu> findmenuInformation(List<Integer> oneLeverMenuIds, List<Integer> menuIdsAll);
}
