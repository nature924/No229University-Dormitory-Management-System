package com.usc.lzh.doms.service.impl;

import com.alibaba.fastjson.JSON;
import com.usc.lzh.doms.utils.MenuNode;
import com.usc.lzh.doms.entity.Menu;
import com.usc.lzh.doms.mapper.MenuMapper;
import com.usc.lzh.doms.service.MenuService;
import com.usc.lzh.doms.utils.TreeUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

@Service
public class MenuServiceImpl implements MenuService {

    @Resource
    private MenuMapper menuMapper;

    /**
     * 加载所有菜单列表
     * @return
     */
    @Override
    public String loadMenuList(Integer utype) {
        // 用来保存menuInfo信息
        Map<String, Object> map = new LinkedHashMap<String, Object>();
        // 保存homeInfo信息
        Map<String, Object> home = new LinkedHashMap<String, Object>();
        // 保存logoInfo信息
        Map<String, Object> logo = new LinkedHashMap<String, Object>();

        // 先查出所有的菜单列表
        List<Menu> menuList = menuMapper.findMenuList(utype);
        // 保存菜单关系
        List<MenuNode> menuNodeList = new ArrayList<>();
        for (Menu menu : menuList) {
            // 遍历菜单列表，创建菜单节点对象，以构建菜单间的层级关系
            MenuNode menuNode = new MenuNode();
            menuNode.setId(menu.getId());
            menuNode.setPid(menu.getPid());
            menuNode.setTitle(menu.getTitle());
            menuNode.setHref(menu.getHref());
            menuNode.setTarget(menu.getTarget());
            menuNode.setIcon(menu.getIcon());
            menuNode.setUtype(menu.getUtype());
            menuNodeList.add(menuNode);
        }
        String href = "";
        switch (utype){
            case 0:
                href = "/student/myDormitoryInfo";
                break;
            case 1:
                href = "/teacher/viewStudentInfo";
                break;
            case 2:
                href = "/dm/buildroomList";
                break;
        }
        home.put("title", "首页");
        home.put("href", href);//控制器路由,自行定义
        logo.put("title", "高校宿舍管理系统");
        logo.put("image", "../images/logo.png");
        logo.put("href", "");
        // 构建init.json数据
        map.put("homeInfo", home);
        map.put("logoInfo", logo);
        map.put("menuInfo", TreeUtil.toTree(menuNodeList, 0));
        return JSON.toJSONString(map);
    }
}
