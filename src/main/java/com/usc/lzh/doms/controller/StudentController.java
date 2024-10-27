package com.usc.lzh.doms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usc.lzh.doms.entity.*;
import com.usc.lzh.doms.service.StudentService;
import com.usc.lzh.doms.utils.DataGridViewResult;
import com.usc.lzh.doms.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping("/student")
public class StudentController {
    @Resource
    private StudentService studentService;

    /**
     * 我的卫生检查记录界面
     *
     * @return
     */
    @RequestMapping(value = "/viewMyCleanInfo")
    public String viewMyCleanInfo() {
        return "/student/clean-list";
    }

    /**
     * 查询我的宿舍卫生检查记录
     *
     * @param ciVo    分页信息
     * @param request 用于获取session中的uid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/clean/list")
    public DataGridViewResult findMyCleanInfoListByPage(CleanInfoVo ciVo, HttpServletRequest request) {
        // 获取学号
        String uid = (String) request.getSession().getAttribute("uid");
        ciVo.setStuid(uid);
        // 设置分页信息
        PageHelper.startPage(ciVo.getPage(), ciVo.getLimit());
        // 查询
        List<CleanInfo> list = studentService.findMyCleanInfoListByPage(ciVo);
        // 创建分页对象
        PageInfo<CleanInfo> pageInfo = new PageInfo<CleanInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 我的报修界面
     *
     * @return
     */
    @RequestMapping(value = "/viewMyRepairInfo")
    public String viewMyRepairInfo() {
        return "/student/repair-list";
    }

    /**
     * 查询我的宿舍的报修记录
     *
     * @param riVo    分页信息
     * @param request 用于获取session中的uid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/repair/list")
    public DataGridViewResult findMyRepairInfoListByPage(RepairInfoVo riVo, HttpServletRequest request) {
        // 获取学号
        String uid = (String) request.getSession().getAttribute("uid");
        riVo.setStuid(uid);
        // 设置分页信息
        PageHelper.startPage(riVo.getPage(), riVo.getLimit());
        // 查询
        List<RepairInfo> list = studentService.findMyRepairInfoListByPage(riVo);
        // 创建分页对象
        PageInfo<RepairInfo> pageInfo = new PageInfo<RepairInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 提交报修单
     *
     * @param ri
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/repair/add")
    public String addRepairInfo(RepairInfo ri, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String stuid = (String) request.getSession().getAttribute("uid");
        ri.setStuid(stuid);
        // 报修提交时间
        Date date = new Date();
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        ri.setSubtime(format.format(date));
        System.out.println(ri);

        // 去执行添加操作
        int result = studentService.addRepairInfo(ri);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "添加成功");
        } else {
            map.put("success", false);
            map.put("msg", "添加失败");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改报修单
     *
     * @param ri
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/repair/update")
    public String updateRepairInfo(RepairInfo ri, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        System.out.println(ri);

        // 判断这是不是自己的提交的报修单
        String stuid = (String) request.getSession().getAttribute("uid");
        if (!stuid.equals(ri.getStuid())) {
            map.put("success", false);
            map.put("msg", "只能修改自己提交的报修单！");
            return JSON.toJSONString(map);
        }

        int result = studentService.updateRepairInfo(ri);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "修改成功！");
        } else {
            map.put("success", false);
            map.put("msg", "修改失败！");
        }
        return JSON.toJSONString(map);
    }


    /**
     * 我的宿舍界面
     *
     * @return
     */
    @RequestMapping(value = "/viewMyDormInfo")
    public String viewMyDormInfo() {
        return "/student/myDorm";
    }


    /**
     * 查看留言板
     *
     * @return
     */
    @RequestMapping(value = "/viewMessageBoard")
    public String viewMessageBoard() {
        return "/student/messageboard";
    }

    @ResponseBody
    @RequestMapping(value = "/message/list")
    public String findMessageListByPage(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 获取id，用来查找所在宿舍区
            String uid = (String) request.getSession().getAttribute("uid");
            // 获取当前页
            Integer curr = Integer.parseInt(request.getParameter("curr"));
            // 获取页面size
            Integer limit = Integer.parseInt(request.getParameter("limit"));
            // 获取信息类型
            Integer type = Integer.parseInt(request.getParameter("type"));
            // 起始行数
            Integer start = (curr - 1) * limit;
            List<MessageBoard> list = studentService.findMessageListByPage(uid, start, limit, type);
            // 总行数
            Integer count = studentService.findMessageCount(type);
            // 总页数
            Integer total = (count - 1) / limit + 1;
            map.put("success", true);
            map.put("count", count);
            map.put("total", total);
            map.put("curr", curr);
            map.put("limit", limit);
            map.put("data", list);
            System.out.println(JSON.toJSONString(map));
            return JSON.toJSONString(map);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "系统错误！");
            return JSON.toJSONString(map);
        }
    }

    /**
     * 查看我的留言
     *
     * @return
     */
    @RequestMapping(value = "viewMyMessage")
    public String viewMyMessage() {
        return "/student/my-message";
    }


    /**
     * 查看我的留言
     *
     * @param mbVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/message/my")
    public DataGridViewResult myMessage(MessageBoardVo mbVo, HttpServletRequest request) {
        String uname = (String) request.getSession().getAttribute("uname");
        mbVo.setAnnouncer(uname);

        // 设置分页信息
        PageHelper.startPage(mbVo.getPage(), mbVo.getLimit());
        // 查询
        List<MessageBoard> list = studentService.findMyMessage(mbVo);
        // 创建分页对象
        PageInfo<MessageBoard> pageInfo = new PageInfo<MessageBoard>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 发布留言
     *
     * @param mb
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/message/add")
    public String addMessage(MessageBoard mb, HttpServletRequest request) {
        String uname = (String) request.getSession().getAttribute("uname");
        mb.setAnnouncer(uname);

        HashMap<String, Object> map = new HashMap<>();
        int result = studentService.addMessage(mb);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "添加成功！");
        } else {
            map.put("success", false);
            map.put("msg", "添加失败！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 批量删除留言
     *
     * @param request 获取前端传来的id数组
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/message/delete")
    public String deleteMessage(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String params = request.getParameter("params");
        System.out.println(params);
        try {
            if (StringUtils.isNotBlank(params)) {
                // 获取id数组
                JSONArray jsonArray = JSONArray.parseArray(params);
                List<Integer> list = new ArrayList<>();
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject obj = jsonArray.getJSONObject(i);
                    Integer id = (Integer) obj.get("id");
                    list.add(id);
                }
                boolean result = studentService.deleteMessage(list);
                if (result) {
                    map.put("success", true);
                    map.put("msg", "删除成功！");
                } else {
                    map.put("success", false);
                    map.put("msg", "删除失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "删除失败！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 查看学生基本信息
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/basic/info")
    public String viewStudentInfo(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();

        String stuid = (String) request.getSession().getAttribute("uid");
        if (StringUtils.isNotBlank(stuid)) {
            StudentInfo si = studentService.findStudentInfoByStuid(stuid);
            if (si != null) {
                map.put("success", true);
                map.put("data", si);
            } else {
                map.put("success", false);
                map.put("msg", "系统错误：不存在该用户！");
            }
        }
        return JSON.toJSONString(map);
    }

    /**
     * 更改基本信息
     *
     * @param si
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/basic/update")
    public String updateStudentInfo(StudentInfo si) {
        HashMap<String, Object> map = new HashMap<>();
        System.out.println(si);
        int result = studentService.updateStudentInfo(si);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "保存成功！");
        } else {
            map.put("success", false);
            map.put("msg", "保存失败！");
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/leave/register")
    public String myLeaveInfo() {
        return "/student/leave-info";
    }

    /**
     * 查找我的离校登记记录
     *
     * @param liVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/leave/list")
    public DataGridViewResult findMyLeaveInfoByPage(LeaveInfoVo liVo, HttpServletRequest request) {
        String stuid = (String) request.getSession().getAttribute("uid");
        liVo.setStuid(stuid);
        System.out.println(liVo);

        // 设置分页信息
        PageHelper.startPage(liVo.getPage(), liVo.getLimit());
        // 查询
        List<LeaveInfo> list = studentService.findMyLeaveInfoByPage(liVo);
        // 创建分页对象
        PageInfo<LeaveInfo> pageInfo = new PageInfo<LeaveInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }


    /**
     * 添加离校登记记录
     *
     * @param li
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/leave/add")
    public String addMyLeaveInfo(LeaveInfo li, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();

        String stuid = (String) request.getSession().getAttribute("uid");
        li.setStuid(stuid);
        System.out.println(li);

        int result = studentService.addMyLeaveInfo(li);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "登记成功！");
        } else {
            map.put("success", false);
            map.put("msg", "登记失败，请稍后再试！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改离校登记记录
     *
     * @param li
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/leave/update")
    public String updateMyLeaveInfo(LeaveInfo li) {
        HashMap<String, Object> map = new HashMap<>();

        System.out.println(li);

        int result = studentService.updateMyLeaveInfo(li);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "更改成功！");
        } else {
            map.put("success", false);
            map.put("msg", "更改失败，请稍后再试！");
        }
        return JSON.toJSONString(map);
    }


    /**
     * 留校申请
     *
     * @return
     */
    @RequestMapping(value = "/stayin/apply")
    public String myStayInfo() {
        return "/student/stayin-apply";
    }

    /**
     * 查看自己的留校申请
     *
     * @param siVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stayin/list")
    public DataGridViewResult findMyStayInfoListByPage(StayInfoVo siVo, HttpServletRequest request) {
        String stuid = (String) request.getSession().getAttribute("uid");
        siVo.setStuid(stuid);
        System.out.println(siVo);

        // 设置分页信息
        PageHelper.startPage(siVo.getPage(), siVo.getLimit());
        // 查询
        List<StayInfo> list = studentService.findMyStayInfoListByPage(siVo);
        // 创建分页对象
        PageInfo<StayInfo> pageInfo = new PageInfo<StayInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 上传图片
     *
     * @param file
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stayin/uploadImage")
    public String uploadOpinionImage(MultipartFile file, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        HashMap<String, Object> data = new HashMap<>();
        try {
            if (file != null) {
                String stuid = (String) request.getSession().getAttribute("uid");
                String originalFilename = file.getOriginalFilename();
                Date date = new Date();
                String fileName = date.getTime() + "-" + originalFilename;
                // 类路径
//                String path = ResourceUtils.getURL("classpath:").getPath();
                String classPath = "F:/IDEA/workspace/高校宿舍管理系统/doms/target/classes/static";
                // 父路径
                String src = "/upload/stayin/" + stuid + "/";
                File directory = new File(classPath, src);
                if (!directory.exists()) {
                    directory.mkdir();
                }
                // 文件名
                String imagePath = directory.getPath() + "\\" + fileName;
                System.out.println(imagePath);
                file.transferTo(new File(imagePath));
                data.put("src", src + fileName);
                map.put("code", 0);
                map.put("msg", "上传成功！");
                map.put("data", data);
                return JSON.toJSONString(map);
            } else {
                map.put("code", -1);
                map.put("msg", "请选择图片！");
                return JSON.toJSONString(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", -1);
            map.put("msg", "上传失败，请稍后重试！");
            return JSON.toJSONString(map);
        }
    }


    /**
     * 提交留校申请
     *
     * @param si
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stayin/add")
    public String addStayInfo(StayInfo si, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String stuid = (String) request.getSession().getAttribute("uid");
        si.setStuid(stuid);

        System.out.println(si);
        int result = studentService.addMyStayInfo(si);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "提交申请成功");
        } else {
            map.put("success", false);
            map.put("msg", "提交申请失败，请稍后重试！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改留校申请信息
     *
     * @param si
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stayin/update")
    public String updateMyStayInfo(StayInfo si) {
        HashMap<String, Object> map = new HashMap<>();

        System.out.println(si);

        int result = studentService.updateMyStayInfo(si);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "修改成功！");
        } else {
            map.put("success", false);
            map.put("msg", "修改失败，请稍后再试！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 查看我的返校信息
     *
     * @return
     */
    @RequestMapping(value = "/viewMyBackInfo")
    public String viewMyBackToScInfo() {
        return "/student/back-info";
    }

    /**
     * 查找我的返校登记记录
     *
     * @param bsVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/back/list")
    public DataGridViewResult findMyBackInfoByPage(BackToScInfoVo bsVo, HttpServletRequest request) {
        String stuid = (String) request.getSession().getAttribute("uid");
        bsVo.setStuid(stuid);
        System.out.println(bsVo);

        // 设置分页信息
        PageHelper.startPage(bsVo.getPage(), bsVo.getLimit());
        // 查询
        List<BackToScInfo> list = studentService.findMyBackInfoByPage(bsVo);
        // 创建分页对象
        PageInfo<BackToScInfo> pageInfo = new PageInfo<BackToScInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }


    /**
     * 添加返校登记记录
     *
     * @param bs
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/back/add")
    public String addMyBackInfo(BackToScInfo bs, HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();

        String stuid = (String) request.getSession().getAttribute("uid");
        bs.setStuid(stuid);
        System.out.println(bs);

        int result = studentService.addMyBackInfo(bs);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "提交成功！");
        } else {
            map.put("success", false);
            map.put("msg", "提交失败，请稍后再试！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 修改返校登记记录
     *
     * @param bs
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/back/update")
    public String updateMyBackInfo(BackToScInfo bs) {
        HashMap<String, Object> map = new HashMap<>();

        System.out.println(bs);

        int result = studentService.updateMyBackInfo(bs);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "更改成功！");
        } else {
            map.put("success", false);
            map.put("msg", "更改失败，请稍后再试！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 密码设置
     *
     * @return
     */
    @RequestMapping(value = "/setting")
    public String passwordSetting() {
        return "password-setting";
    }

    @RequestMapping(value = "/myDormitoryInfo")
    public String viewMyDormitoryInfo() {
        return "/student/my-dormitory";
    }

    /**
     * 查看自己宿舍信息
     *
     * @param aiVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mydorm/list")
    public DataGridViewResult findMyDormitoryInfoListByPage(AllocationInfoVo aiVo, HttpServletRequest request) {
        String stuid = (String) request.getSession().getAttribute("uid");
        aiVo.setStuid(stuid);
        System.out.println(aiVo);

        // 设置分页信息
        PageHelper.startPage(aiVo.getPage(), aiVo.getLimit());
        // 查询
        List<AllocationInfo> list = studentService.findMyDormitoryInfoListByPage(aiVo);
        // 创建分页对象
        PageInfo<AllocationInfo> pageInfo = new PageInfo<AllocationInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 判断是否已经选择床位了
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mydorm/isChoosed")
    public String isChooseBed(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String stuid = (String) request.getSession().getAttribute("uid");
        boolean status = studentService.isChooseBed(stuid);
        map.put("status", status);
        return JSON.toJSONString(map);
    }

    /**
     * 提交选择的床位号
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mydorm/chooseBed")
    public String chooseBed(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String stuid = (String) request.getSession().getAttribute("uid");
        String bed = request.getParameter("bed");
        if (StringUtils.isNotBlank(bed)) {
            int bedNum = Integer.parseInt(bed);
            // 提交床位号
            int result = studentService.chooseBed(stuid, bedNum);
            if (result > 0) {
                map.put("success", true);
                map.put("msg", "选择成功！");
            } else {
                map.put("success", false);
                map.put("msg", "选择失败！");
            }
            return JSON.toJSONString(map);
        } else {
            map.put("success", false);
            map.put("msg", "床位号不能为空！");
            return JSON.toJSONString(map);
        }
    }

    /**
     * 查找已经选择了的床位
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/mydorm/alreadyBeds")
    public String findAlreadyChooseBeds(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String stuid = (String) request.getSession().getAttribute("uid");
        List<Integer> list = studentService.findAlreadyChooseBeds(stuid);
        map.put("beds", list);
        return JSON.toJSONString(map);
    }
}
