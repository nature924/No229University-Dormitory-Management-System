package com.usc.lzh.doms.mapper;

import com.usc.lzh.doms.entity.Menu;

import java.util.List;

public interface MenuMapper {
    /**
     * 查询所有菜单
     * @return
     */
    public List<Menu> findMenuList(Integer utype);
}
