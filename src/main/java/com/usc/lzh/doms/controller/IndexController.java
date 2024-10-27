package com.usc.lzh.doms.controller;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.usc.lzh.doms.entity.Users;
import com.usc.lzh.doms.service.IndexService;
import com.usc.lzh.doms.service.MenuService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/")
public class IndexController {
    @Resource
    private IndexService indexService;
    @Resource
    private MenuService menuService;

    public String index() {
        return "index";
    }

    @RequestMapping(value = "/login.html")
    public String toLogin() {
        return "/login";
    }

    @RequestMapping(value = "/home.html")
    public String home() {
        return "/home";
    }


    /**
     * 验证登录
     *
     * @param re      前端返回的参数
     * @param session 将用户信息添加到session中
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/login.action")
    public String loginAction(@RequestBody Map<String, String> re, HttpSession session) {
        String uid = re.get("username");
        String upwd = re.get("password");
        Integer utype = Integer.parseInt(re.get("type"));
        System.out.println(utype);
        Users user = indexService.findUserByuId(uid, upwd, utype);
        Map<String, Object> map = new HashMap<>();
        if (user != null) {
            session.setAttribute("uid", uid);
            session.setAttribute("uname", user.getUname());
            session.setAttribute("utype", utype);
            // 如果是教师或宿管员，还要把他们负责的部门（专业年级/宿舍楼）记下
            if (utype == 1) {
                session.setAttribute("dept", user.getDept());
                session.setAttribute("grade", user.getGrade());
            } else if (utype == 2) {
                session.setAttribute("brarea", user.getBrarea());
                session.setAttribute("brbid", user.getBrbid());
            }
            map.put("type", "success");
        } else {
            map.put("type", "error");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 退出登录
     *
     * @param session
     * @return 返回到登录界面
     */
    @RequestMapping(value = "/logout.action")
    public String logout(HttpSession session) {
        // 清空session中的属性
        session.removeAttribute("uid");
        session.removeAttribute("uname");
        session.removeAttribute("utype");
        //让session无效
        session.invalidate();
        return "redirect:/login";
    }

    @ResponseBody
    @RequestMapping(value = "/api/loadMenuList")
    public String loadMenuList(HttpSession session) {
        Integer utype = (Integer) session.getAttribute("utype");
        String initJson = menuService.loadMenuList(utype);
        System.out.println(initJson);
        return initJson;
    }

    /**
     * 基本资料
     *
     * @param session
     * @return
     */
    @RequestMapping(value = "/basic-info.html")
    public String setBasicInfo(HttpSession session) {
        Integer utype = (Integer) session.getAttribute("utype");
        // 是学生则返回学生的界面
        if (utype == 0) {
            return "/student/basic-info";
        } else {
            return "/basic-info";
        }
    }


    /**
     * 修改密码
     *
     * @return
     */
    @RequestMapping(value = "/password-setting.html")
    public String setPassword() {
        return "/password-setting";
    }

    @ResponseBody
    @RequestMapping(value = "/updatePassword.action")
    public String updatePassword(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();

        String uid = (String) request.getSession().getAttribute("uid");
        Integer utype = (Integer) request.getSession().getAttribute("utype");
        String param = request.getParameter("param");
        System.out.println(param);
        try {
            if (StringUtils.isNotBlank(param)) {
                JSONObject obj = JSONObject.parseObject(param);
                String old_password = (String) obj.get("old_password");
                String new_password = (String) obj.get("new_password");
                int result = indexService.updatePassword(uid, utype, old_password, new_password);
                switch (result) {
                    case -1:
                        map.put("success", false);
                        map.put("msg", "系统出错，修改失败！");
                        break;
                    case 0:
                        map.put("success", false);
                        map.put("msg", "旧密码不正确！");
                        break;
                    case 1:
                        map.put("success", true);
                        map.put("msg", "修改成功！");
                }
                return JSON.toJSONString(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "系统出错，修改失败！");
        }
        return JSON.toJSONString(map);
    }
}
