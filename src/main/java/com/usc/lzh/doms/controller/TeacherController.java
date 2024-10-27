package com.usc.lzh.doms.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usc.lzh.doms.entity.*;
import com.usc.lzh.doms.service.TeacherService;
import com.usc.lzh.doms.utils.DataGridViewResult;
import com.usc.lzh.doms.utils.ExcelUtils;
import com.usc.lzh.doms.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 教师对应的控制器
 */
@Controller
@RequestMapping("/teacher")
public class TeacherController {
    @Resource
    private TeacherService teacherService;

    @RequestMapping(value = "/viewAllocationInfo")
    public String allocationList() {
        return "/teacher/allocation-list";
    }

    /**
     * 查询宿舍分配信息
     * 查询条件：专业/年级
     *
     * @param aiVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/allocation/list")
    public DataGridViewResult findAllocationInfoList(AllocationInfoVo aiVo, HttpServletRequest request) {
        // 获取session中的专业和年级
        String dept = (String) request.getSession().getAttribute("dept");
        String grade = (String) request.getSession().getAttribute("grade");
        if (StringUtils.isNotBlank(dept)) {
            aiVo.setStudept(dept);
        }
        if (StringUtils.isNotBlank(grade)) {
            aiVo.setStugrade(grade);
        }
        System.out.println(aiVo);
        // 设置分页信息
        PageHelper.startPage(aiVo.getPage(), aiVo.getLimit());
        // 查询
        List<AllocationInfo> list = teacherService.findAllocationInfoListByPage(aiVo);
        // 创建分页对象
        PageInfo<AllocationInfo> pageInfo = new PageInfo<AllocationInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 添加宿舍分配信息
     *
     * @param ai
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/allocation/add")
    public String addAllocationInfo(AllocationInfo ai) {
        HashMap<String, Object> map = new HashMap<>();
        int result = teacherService.addAllocationInfo(ai);
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
     * 更改宿舍分配信息
     *
     * @param ai
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/allocation/update")
    public String updateAllocationInfo(AllocationInfo ai) {
        HashMap<String, Object> map = new HashMap<>();
        int result = teacherService.updateAllocationInfo(ai);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "更改成功！");
        } else {
            map.put("success", false);
            map.put("msg", "更改失败！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除宿舍分配信息
     *
     * @param stuid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/allocation/delete")
    public String deleteAllocationInfo(String stuid) {
        HashMap<String, Object> map = new HashMap<>();
        int result = teacherService.deleteAllocationInfo(stuid);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "删除成功！");
        } else {
            map.put("success", false);
            map.put("msg", "删除失败！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 导出宿舍分配信息列表（excel格式）
     *
     * @param request  用来获取缓存中的attribute
     * @param response 输出文件流
     */
    @ResponseBody
    @RequestMapping(value = "/allocation/export.action")
    public void exportAllocationInfoToExcel(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>();
        response.reset();// 清除缓存
        String studept = (String) request.getSession().getAttribute("dept");
        String grade = (String) request.getSession().getAttribute("grade");
        System.out.println("studept" + studept);
        System.out.println("grade" + grade);
        // 查找宿舍分配信息列表
        List<AllocationInfo> list = teacherService.exportByDept(studept, grade);

        // 拼接excel表名
        StringBuffer filenamebuffer = new StringBuffer();
        if (StringUtils.isNotBlank(studept)) {
            filenamebuffer.append(studept);
            filenamebuffer.append("-");
        }
        if (StringUtils.isNotBlank(grade)) {
            filenamebuffer.append(grade);
            filenamebuffer.append("-");
        }
        filenamebuffer.append("宿舍分配表");
        String filename = filenamebuffer.toString();
        try {
            ExcelUtils.writeExcel(filename, response, list, AllocationInfo.class);
            map.put("success", true);
            map.put("mag", "导出成功！");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("mag", "导出失败！");
        }
//        return JSON.toJSONString(map);
    }


    @RequestMapping(value = "/viewStudentInfo")
    public String studentList() {
        return "/teacher/student-list";
    }

    /**
     * 获取学生信息列表
     *
     * @param si
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/student/list")
    public DataGridViewResult findStudentInfoList(StudentInfoVo si, HttpServletRequest request) {
        // 获取教师所负责的专业和年级,年级对应学号
        String studept = (String) request.getSession().getAttribute("dept");
        String grade = (String) request.getSession().getAttribute("grade");
        if (StringUtils.isNotBlank(studept)) {
            si.setStudept(studept);
        }
        // 如果学号为空，则教师负责的年级作为学号的前缀条件
        if (StringUtils.isBlank(si.getStuid())) {
            if (StringUtils.isNotBlank(grade)) {
                si.setStuid(grade);
            }
        }
        System.out.println(si);

        // 设置分页信息
        PageHelper.startPage(si.getPage(), si.getLimit());
        // 查询
        List<StudentInfo> list = teacherService.findStudentInfoListByPage(si);
        // 创建分页对象
        PageInfo<StudentInfo> pageInfo = new PageInfo<StudentInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }


    /**
     * 添加学生信息
     *
     * @param si
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/student/add")
    public String addStudentInfo(StudentInfo si) {
        HashMap<String, Object> map = new HashMap<>();
        String grade = si.getStuid().substring(0, 4);
        si.setStugrade(grade);
        System.out.println(grade);
        int result = teacherService.addStudentInfo(si);
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
     * 更改学生信息
     *
     * @param si
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/student/update")
    public String updateStudentInfo(StudentInfo si) {
        HashMap<String, Object> map = new HashMap<>();
        int result = teacherService.updateStudentInfo(si);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "更改成功！");
        } else {
            map.put("success", false);
            map.put("msg", "更改失败！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除学生信息
     *
     * @param stuid
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/student/delete")
    public String deleteaddStudentInfo(String stuid) {
        HashMap<String, Object> map = new HashMap<>();
        int result = teacherService.deleteStudentInfo(stuid);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "删除成功！");
        } else {
            map.put("success", false);
            map.put("msg", "删除失败！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 导入学生信息
     *
     * @param file excel表格
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/student/import.action")
    public String importExcel(@RequestParam("file") MultipartFile file) {
        HashMap<String, Object> map = new HashMap<>();

        try {
            List<StudentInfo> list = ExcelUtils.readExcel("", StudentInfo.class, file);
            for (int i = 0; i < list.size(); i++) {
                String grade = list.get(i).getStuid().substring(0, 4);
                list.get(i).setStugrade(grade);
                System.out.println(grade);
            }
            boolean result = teacherService.batchInsert(list);
            if (result) {
                map.put("code", 200);
                map.put("msg", "导入成功！");
                map.put("data", null);
            } else {
                map.put("code", 500);
                map.put("msg", "导入失败！");
                map.put("data", null);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 500);
            map.put("msg", "导入失败，请检查文件内容是否正确。");
            map.put("data", null);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 查看卫生检查情况
     *
     * @return
     */
    @RequestMapping(value = "/viewCleanInfo")
    public String viewCleanInfo() {
        return "/teacher/clean-list";
    }

    /**
     * 专业班级卫生检查信息
     *
     * @param ciVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/clean/list")
    public DataGridViewResult findCleanInfoListByPage(CleanInfoVo ciVo, HttpServletRequest request) {
        // 获取session中的专业和年级
        String dept = (String) request.getSession().getAttribute("dept");
        String grade = (String) request.getSession().getAttribute("grade");
        if (StringUtils.isNotBlank(dept)) {
            ciVo.setStudept(dept);
        }
        if (StringUtils.isNotBlank(grade)) {
            ciVo.setStuid(grade);
        }
        System.out.println(ciVo);
        // 设置分页信息
        PageHelper.startPage(ciVo.getPage(), ciVo.getLimit());
        // 查询
        List<CleanInfoVo> list = teacherService.findCleanInfoListByPage(ciVo);
        // 创建分页对象
        PageInfo<CleanInfoVo> pageInfo = new PageInfo<CleanInfoVo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    @RequestMapping(value = "/viewLeaveInfo")
    public String viewLeaveInfo() {
        return "/teacher/leave-info";
    }

    @ResponseBody
    @RequestMapping(value = "/leave/list")
    public DataGridViewResult findLeaveInfoByPage(LeaveInfoVo liVo, HttpServletRequest request) {
        // 专业和年级
        String dept = (String) request.getSession().getAttribute("dept");
        String grade = (String) request.getSession().getAttribute("grade");
        if (StringUtils.isNotBlank(dept)) {
            liVo.setStudept(dept);
        }
        if (StringUtils.isNotBlank(grade)) {
            liVo.setStugrade(grade);
        }
        System.out.println(liVo);
        // 设置分页信息
        PageHelper.startPage(liVo.getPage(), liVo.getLimit());
        // 查询
        List<LeaveInfo> list = teacherService.findLeaveInfoListByPage(liVo);
        // 创建分页对象
        PageInfo<LeaveInfo> pageInfo = new PageInfo<LeaveInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/leave/delete")
    public String batchDeleteLeaveInfo(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String params = request.getParameter("params");
        try {
            if (StringUtils.isBlank(params)) {
                map.put("success", false);
                map.put("msg", "请选择要删除的行！");
                return JSON.toJSONString(map);
            }
            boolean result = teacherService.batchDeleteLeaveInfo(params);
            if (result) {
                map.put("success", true);
                map.put("msg", "删除成功！");
            } else {
                map.put("success", false);
                map.put("msg", "系统错误，请稍后再试！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "系统错误，请稍后再试！");
        }
        return JSON.toJSONString(map);
    }


    /**
     * 将学生去向信息导出到Excel中
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/leave/export.action")
    public void exportLeaveInfoToExcel(HttpServletRequest request, HttpServletResponse response) {
        response.reset();// 清除缓存
        String studept = (String) request.getSession().getAttribute("dept");
        String stugrade = (String) request.getSession().getAttribute("grade");
        // 查找学生登记信息
        List<LeaveInfo> list = teacherService.exportLeaveInfo(studept, stugrade);

        // 拼接excel表名
        StringBuffer filenamebuffer = new StringBuffer();
        if (StringUtils.isNotBlank(studept)) {
            filenamebuffer.append(studept);
            filenamebuffer.append("-");
        }
        if (StringUtils.isNotBlank(stugrade)) {
            filenamebuffer.append(stugrade);
            filenamebuffer.append("级-");
        }
        filenamebuffer.append("节假日去向表");
        String filename = filenamebuffer.toString();
        try {
            ExcelUtils.writeExcel(filename, response, list, LeaveInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取饼状图的数据
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/leave/echartsData")
    public String getLeaveInfoEchartsData(HttpServletRequest request) {
        String studept = (String) request.getSession().getAttribute("dept");
        String stugrade = (String) request.getSession().getAttribute("grade");
        HashMap<String, Object> map = teacherService.getLeaveInfoEchartsData(studept, stugrade);
        String result = JSON.toJSONString(map);
        System.out.println(result);
        return result;
    }


    /**
     * 留校管理
     *
     * @return
     */
    @RequestMapping(value = "/stayinManage")
    public String stayinManege() {
        return "/teacher/stayin-manage";
    }

    /**
     * 留校申请列表
     *
     * @param siVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stayin/list")
    public DataGridViewResult findStayInfoListByPage(StayInfoVo siVo, HttpServletRequest request) {
        String studept = (String) request.getSession().getAttribute("dept");
        String stugrade = (String) request.getSession().getAttribute("grade");
        if (StringUtils.isNotBlank(studept)) {
            siVo.setStudept(studept);
        }
        if (StringUtils.isNotBlank(stugrade)) {
            siVo.setStugrade(stugrade);
        }
        System.out.println(siVo);

        // 设置分页信息
        PageHelper.startPage(siVo.getPage(), siVo.getLimit());
        // 查询
        List<StayInfo> list = teacherService.findStayInfoListByPage(siVo);
        // 创建分页对象
        PageInfo<StayInfo> pageInfo = new PageInfo<StayInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 审批学生留校申请
     *
     * @param si
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stayin/approve")
    public String approveStayInfo(StayInfo si) {
        HashMap<String, Object> map = new HashMap<>();
        System.out.println(si);
        int result = teacherService.approveStayInfo(si);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "审批成功");
        } else {
            map.put("success", false);
            map.put("msg", "审批失败，请稍后再试！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 导出学生留校申请信息
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/stayin/export.action")
    public void exportStayInfoToExcel(HttpServletRequest request, HttpServletResponse response) {
        response.reset();// 清除缓存
        String studept = (String) request.getSession().getAttribute("dept");
        String stugrade = (String) request.getSession().getAttribute("grade");
        // 查找学生登记信息
        List<StayInfo> list = teacherService.exportStayInfo(studept, stugrade);

        // 拼接excel表名
        StringBuffer filenamebuffer = new StringBuffer();
        if (StringUtils.isNotBlank(studept)) {
            filenamebuffer.append(studept);
            filenamebuffer.append("-");
        }
        if (StringUtils.isNotBlank(stugrade)) {
            filenamebuffer.append(stugrade);
            filenamebuffer.append("级-");
        }
        filenamebuffer.append("留校申请表");
        String filename = filenamebuffer.toString();
        try {
            ExcelUtils.writeExcel(filename, response, list, StayInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取留校申请中的统计数据
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stayin/echartsData")
    public String getStayInfoEchartsData(HttpServletRequest request) {
        String studept = (String) request.getSession().getAttribute("dept");
        String stugrade = (String) request.getSession().getAttribute("grade");
        JSONObject data = teacherService.getStayInfoEchartsData(studept, stugrade);
        System.out.println(JSON.toJSONString(data));
        return JSON.toJSONString(data);
    }

    @RequestMapping(value = "/backManage")
    public String backManage() {
        return "/teacher/back-manage";
    }

    /**
     * 查找学生返校信息
     *
     * @param bsVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/back/list")
    public DataGridViewResult findBackToScInfoListByPage(BackToScInfoVo bsVo, HttpServletRequest request) {
        String studept = (String) request.getSession().getAttribute("dept");
        String stugrade = (String) request.getSession().getAttribute("grade");
        if (StringUtils.isNotBlank(studept)) {
            bsVo.setStudept(studept);
        }
        if (StringUtils.isNotBlank(stugrade)) {
            bsVo.setStugrade(stugrade);
        }
        System.out.println(bsVo);

        // 设置分页信息
        PageHelper.startPage(bsVo.getPage(), bsVo.getLimit());
        // 查询
        List<BackToScInfo> list = teacherService.findBackToScInfoListByPage(bsVo);
        // 创建分页对象
        PageInfo<BackToScInfo> pageInfo = new PageInfo<BackToScInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 导出学生返校登记信息
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/back/export.action")
    public void exportBackToScInfoToExcel(HttpServletRequest request, HttpServletResponse response) {
        response.reset();// 清除缓存
        String studept = (String) request.getSession().getAttribute("dept");
        String stugrade = (String) request.getSession().getAttribute("grade");
        // 查找学生登记信息
        List<BackToScInfo> list = teacherService.exportBackToScInfo(studept, stugrade);

        // 拼接excel表名
        StringBuffer filenamebuffer = new StringBuffer();
        if (StringUtils.isNotBlank(studept)) {
            filenamebuffer.append(studept);
            filenamebuffer.append("-");
        }
        if (StringUtils.isNotBlank(stugrade)) {
            filenamebuffer.append(stugrade);
            filenamebuffer.append("级-");
        }
        filenamebuffer.append("返校登记表");
        String filename = filenamebuffer.toString();
        try {
            ExcelUtils.writeExcel(filename, response, list, BackToScInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取返校登记的统计数据
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/back/echartsData")
    public String getBackToScInfoEchartsData(HttpServletRequest request) {
        String studept = (String) request.getSession().getAttribute("dept");
        String stugrade = (String) request.getSession().getAttribute("grade");
        JSONObject data = teacherService.getBackToScInfoEchartsData(studept, stugrade);
        System.out.println(JSON.toJSONString(data));
        return JSON.toJSONString(data);
    }
}
