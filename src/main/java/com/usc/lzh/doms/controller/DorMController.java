package com.usc.lzh.doms.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.usc.lzh.doms.entity.*;
import com.usc.lzh.doms.service.DorMService;
import com.usc.lzh.doms.utils.MyStringUtil;
import com.usc.lzh.doms.utils.DataGridViewResult;
import com.usc.lzh.doms.utils.ExcelUtils;
import com.usc.lzh.doms.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSON;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
@RequestMapping("/dm")
public class DorMController {

    @Resource
    private DorMService dormService;

    /**
     * 卫生检查列表
     *
     * @return
     */
    @RequestMapping(value = "/cleanList")
    public String cleanList() {
        return "/dm/clean-list";
    }

    /**
     * 查看卫生检查情况
     *
     * @param ciVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/clean/list")
    public DataGridViewResult findCleanInfoList(CleanInfoVo ciVo, HttpServletRequest request) {
        // 获取宿管员管理的宿舍区和楼栋号，拼接成brcode
        String brarea = (String) request.getSession().getAttribute("brarea");
        String brbid = (String) request.getSession().getAttribute("brbid");
        String brcode = MyStringUtil.getBrcode(brarea, brbid, "");
        if (StringUtils.isNotBlank(brcode)) {
            ciVo.setBrcode(brcode);
        }
        System.out.println(ciVo);

        // 设置分页信息
        PageHelper.startPage(ciVo.getPage(), ciVo.getLimit());
        // 查询
        List<CleanInfo> list = dormService.findCleanInfoListByPage(ciVo);
        // 创建分页对象
        PageInfo<CleanInfo> pageInfo = new PageInfo<CleanInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 更改卫生检查信息
     *
     * @param ci
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/clean/update")
    public String updateCleanInfo(CleanInfo ci) {
        System.out.println(ci);
        HashMap<String, Object> map = new HashMap<>();
        int result = dormService.updateCleanInfo(ci);
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
     * 删除卫生检查记录
     *
     * @param id
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/clean/delete")
    public String deleteCleanInfo(String id) {
        System.out.println(id);
        HashMap<String, Object> map = new HashMap<>();
        int result = dormService.deleteCleanInfo(id);
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
     * 返回添加卫生检查记录页面
     *
     * @return
     */
    @RequestMapping(value = "/clean/add.html")
    public String addCleanInfo() {
        return "/dm/clean-add";
    }

    /**
     * 批量添加卫生检查记录
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/clean/add")
    public String batchAddCleanInfo(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String params = request.getParameter("params");
        String checker = (String) request.getSession().getAttribute("uname");
        try {
            boolean result = dormService.batchInsertCleanInfo(params, checker);
            if (result) {
                map.put("success", true);
                map.put("msg", "添加成功！");
            } else {
                map.put("success", false);
                map.put("msg", "添加失败！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "添加失败！");
        }
        return JSON.toJSONString(map);
    }


    /**
     * 学生报修列表
     *
     * @return
     */
    @RequestMapping(value = "/repairList")
    public String viewRepairList() {
        return "/dm/repair-list";
    }

    /**
     * 查找该宿管员负责的楼栋下的报修信息
     *
     * @param riVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/repair/list")
    public DataGridViewResult findRepairInfoList(RepairInfoVo riVo, HttpServletRequest request) {
        // 拼接brcode
        String brarea = (String) request.getSession().getAttribute("brarea");
        String brbid = (String) request.getSession().getAttribute("brbid");
        if (StringUtils.isBlank(brarea)) {
            brarea = riVo.getBrarea();
        }
        if (StringUtils.isBlank(brbid)) {
            brbid = riVo.getBrbid();
        }
        String brcode = MyStringUtil.getBrcode(brarea, brbid, "");
        if (StringUtils.isNotBlank(brcode)) {
            riVo.setBrcode(brcode);
        }
        System.out.println(riVo);

        // 设置分页信息
        PageHelper.startPage(riVo.getPage(), riVo.getLimit());
        // 查询
        List<RepairInfo> list = dormService.findRepairInfoListByPage(riVo);
        // 创建分页对象
        PageInfo<RepairInfo> pageInfo = new PageInfo<RepairInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 查看报修详情
     *
     * @return
     */
    @RequestMapping(value = "/repair/detail.html")
    public String repairDetail() {
        return "/dm/repair-detail";
    }


    /**
     * 导出报修数据
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/repair/export.action")
    public void exportToExcel(HttpServletRequest request, HttpServletResponse response) {
        HashMap<String, Object> map = new HashMap<>();
        response.reset();// 清除缓存

        // 获取宿管员管理的宿舍区和楼栋号，拼接成brcode
        String brarea = (String) request.getSession().getAttribute("brarea");
        String brbid = (String) request.getSession().getAttribute("brbid");
        String brcode = MyStringUtil.getBrcode(brarea, brbid, "");
        String status = request.getParameter("status");

        System.out.println("status" + status);
        System.out.println("brcode" + brcode);
        // 根据条件查找报修列表
        List<RepairInfo> list = dormService.exportRepairInfo(brcode, status);

        // 拼接excel表名
        StringBuffer filenamebuffer = new StringBuffer();
        if (StringUtils.isNotBlank(brcode)) {
            filenamebuffer.append(brcode);
            filenamebuffer.append("-");
        }
        filenamebuffer.append("报修表");
        String filename = filenamebuffer.toString();
        try {
            ExcelUtils.writeExcel(filename, response, list, RepairInfo.class);
            map.put("success", true);
            map.put("msg", "导出成功！");
            System.out.println(JSON.toJSONString(map));
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "导出失败！");
        }
//        return JSON.toJSONString(map);
    }

    /**
     * 批量更改报修状态
     *
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/repair/edit")
    public String editRepairStatus(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String params = request.getParameter("params");
        if (StringUtils.isNotBlank(params)) {
            boolean result = dormService.batchEditRepairStatus(params);
            if (result) {
                map.put("success", true);
                map.put("msg", "更改成功！");
                return JSON.toJSONString(map);
            } else {
                map.put("success", false);
                map.put("msg", "更改失败！");
                return JSON.toJSONString(map);
            }
        }
        map.put("success", false);
        map.put("msg", "更改失败！请选择要更改的行。");
        return JSON.toJSONString(map);
    }

    /**
     * 学生报修列表
     *
     * @return
     */
    @RequestMapping(value = "/buildroomList")
    public String viewBuildRoomList() {
        return "/dm/buildroom-list";
    }

    /**
     * 查看宿舍信息
     *
     * @param biVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/buildroom/list")
    public DataGridViewResult findBuildRoomInfo(BuildRoomInfoVo biVo, HttpServletRequest request) {
        // 拼接brcode
        String brarea = (String) request.getSession().getAttribute("brarea");
        if (StringUtils.isBlank(brarea)) {
            brarea = biVo.getBrarea();
        }
        /*
         * 如果管理员负责具体的一栋楼，则只能查询本栋楼有关的宿舍信息；
         * 如果是负责一个区，则可以查询这个区下所有楼的宿舍信息
         * session中没有brbid说明是负责一个区的，此时按照用户输入的楼栋数来进行查询
         */
        String brbid = (String) request.getSession().getAttribute("brbid");
        if (StringUtils.isBlank(brbid)) {
            brbid = biVo.getBrbid();
        }
        String brrid = biVo.getBrrid();
        String brcode = MyStringUtil.getBrcode(brarea, brbid, brrid);
        if (StringUtils.isNotBlank(brcode)) {
            biVo.setBrcode(brcode);
        }
        System.out.println(biVo);

        // 设置分页信息
        PageHelper.startPage(biVo.getPage(), biVo.getLimit());
        // 查询
        List<RepairInfo> list = dormService.findBuildRoomInfoListByPage(biVo);
        // 创建分页对象
        PageInfo<RepairInfo> pageInfo = new PageInfo<RepairInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 单个添加宿舍信息
     *
     * @param bi
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/buildroom/add")
    public String addBuildRoomInfo(BuildRoomInfo bi) {
        HashMap<String, Object> map = new HashMap<>();
        // 拼接brcode，如果brcode是空，说明宿舍信息错误了
        String brarea = bi.getBrarea();
        String brbid = bi.getBrbid();
        String brrid = bi.getBrrid();
        String brcode = MyStringUtil.getBrcode(brarea, brbid, brrid);
        if (StringUtils.isBlank(brcode)) {
            map.put("success", false);
            map.put("msg", "添加失败！宿舍信息错误！");
            return JSON.toJSONString(map);
        }

        // 计算空余数
        Integer free = bi.getVolume() - bi.getPeople();
        if (free < 0) {
            map.put("success", false);
            map.put("msg", "添加失败！入住数不能大于床位数！");
            return JSON.toJSONString(map);
        }

        bi.setBrcode(brcode);
        bi.setFree(free);
        System.out.println(bi);
        List<BuildRoomInfo> list = new ArrayList<>();
        list.add(bi);

        boolean result = dormService.addBuildRoomInfo(list);
        if (result) {
            map.put("success", true);
            map.put("msg", "添加成功！");
        } else {
            map.put("success", false);
            map.put("msg", "添加失败！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 导入宿舍信息
     *
     * @param file excel表格
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/buildroom/import.action")
    public String importExcel(@RequestParam("file") MultipartFile file) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            List<BuildRoomInfo> list = ExcelUtils.readExcel("", BuildRoomInfo.class, file);
            // 拼接brcode和计算空余数
            for (int i = 0; i < list.size(); i++) {
                String brarea = list.get(i).getBrarea();
                String brbid = list.get(i).getBrbid();
                String brrid = list.get(i).getBrrid();
                String brcode = MyStringUtil.getBrcode(brarea, brbid, brrid);
                Integer free = list.get(i).getVolume() - list.get(i).getPeople();
                list.get(i).setBrcode(brcode);
                list.get(i).setFree(free);
            }
            boolean result = dormService.addBuildRoomInfo(list);
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
            map.put("msg", "导入失败！");
            map.put("data", null);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 更改宿舍信息
     *
     * @param bi
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/buildroom/update")
    public String updateBuildRoomInfo(BuildRoomInfo bi) {
        HashMap<String, Object> map = new HashMap<>();
        try {
            // 如果入住数小于等于床位数，则执行更新操作，否则返回提示
            Integer free = bi.getVolume() - bi.getPeople();
            if (free >= 0) {
                bi.setFree(free);
                System.out.println(bi);
                int result = dormService.updateBuildRoomInfo(bi);
                // 返回值大于0表示成功执行了更改操作，小于0表示发生了异常
                if (result > 0) {
                    map.put("success", true);
                    map.put("msg", "更改成功！");
                    return JSON.toJSONString(map);
                } else {
                    map.put("success", false);
                    map.put("msg", "更改失败！");
                    return JSON.toJSONString(map);
                }
            } else {
                map.put("success", false);
                map.put("msg", "更改失败！入住数不能大于床位数！");
                return JSON.toJSONString(map);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "更改失败！");
            return JSON.toJSONString(map);
        }
    }

    /**
     * 删除宿舍信息
     *
     * @param brcode
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/buildroom/delete")
    public String deleteBuildRoomInfo(String brcode) {
        System.out.println(brcode);
        HashMap<String, Object> map = new HashMap<>();
        int result = dormService.deleteBuildRoomInfo(brcode);
        if (result > 0) {
            map.put("success", true);
            map.put("msg", "删除成功！");
        } else {
            map.put("success", false);
            map.put("msg", "删除失败！");
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/messageList")
    public String viewMessageBoard() {
        return "/dm/message-list";
    }


    /**
     * 公告管理、失物招领
     *
     * @param mbVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/message/list")
    public DataGridViewResult findMessageList(MessageBoardVo mbVo, HttpServletRequest request) {
        // 获取当前管理员的管理区域
        // 如果管理员负责众多楼栋，则按查询条件的宿舍区和楼栋来查，否则只能查他所负责的楼栋的公告信息
        String brarea = (String) request.getSession().getAttribute("brarea");
        String brbid = (String) request.getSession().getAttribute("brbid");
        if (StringUtils.isBlank(brarea)) {
            brarea = mbVo.getBrarea();
        }
        if (StringUtils.isBlank(brbid)) {
            brbid = mbVo.getBrbid();
        }
        String brcode = MyStringUtil.getBrcode(brarea, brbid, "");
        mbVo.setBrcode(brcode);
        System.out.println(mbVo);

        // 设置分页信息
        PageHelper.startPage(mbVo.getPage(), mbVo.getLimit());
        // 查询
        List<MessageBoard> list = dormService.findMessageListByPage(mbVo);
        // 创建分页对象
        PageInfo<MessageBoard> pageInfo = new PageInfo<MessageBoard>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 添加公告/失物招领信息
     *
     * @param mb
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/message/add")
    public String addMessage(MessageBoard mb, HttpServletRequest request) {
        // announcer是管理员的uname
        String uname = (String) request.getSession().getAttribute("uname");
        mb.setAnnouncer(uname);

        HashMap<String, Object> map = new HashMap<>();
        int result = dormService.addMessage(mb);
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
     * 更新公告/失物招领
     *
     * @param mb
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/message/update")
    public String updateMessage(MessageBoard mb) {
        // 拼接brcode
        String brcode = MyStringUtil.getBrcode(mb.getBrarea(), mb.getBrbid(), "");
        mb.setBrcode(brcode);

        System.out.println(mb);

        HashMap<String, Object> map = new HashMap<>();
        int result = dormService.updateMessage(mb);
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
     * 批量删除公告/失物招领信息
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
                    System.out.println(id);
                    list.add(id);
                }
                boolean result = dormService.deleteMessage(list);
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
     * 留校信息列表
     *
     * @return
     */
    @RequestMapping(value = "/stayinList")
    public String viewStayInfoList() {
        return "/dm/stayin-list";
    }

    /**
     * 查找留校信息列表
     *
     * @param stVo
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/stayin/list")
    public DataGridViewResult findStayInfoListByPage(StayInfoVo stVo, HttpServletRequest request) {
        String brarea = (String) request.getSession().getAttribute("brarea");
        String brbid = (String) request.getSession().getAttribute("brbid");
        if (StringUtils.isNotBlank(brarea)) {
            stVo.setBrarea(brarea);
        }
        if (StringUtils.isNotBlank(brbid)) {
            stVo.setBrbid(brbid);
        }
        System.out.println(stVo);
        // 设置分页信息
        PageHelper.startPage(stVo.getPage(), stVo.getLimit());
        // 查询
        List<StayInfo> list = dormService.findStayInfoListByPage(stVo);
        // 创建分页对象
        PageInfo<StayInfo> pageInfo = new PageInfo<StayInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 导出学生留校信息
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/stayin/export.action")
    public void exportStayInfoToExcel(HttpServletRequest request, HttpServletResponse response) {
        response.reset();// 清除缓存
        String brarea = (String) request.getSession().getAttribute("brarea");
        String brbid = (String) request.getSession().getAttribute("brbid");
        // 查找学生登记信息
        List<StayInfo> list = dormService.exportStayInfo(brarea, brbid);

        // 拼接excel表名
        StringBuffer filenamebuffer = new StringBuffer();
        if (StringUtils.isNotBlank(brarea)) {
            filenamebuffer.append(brarea);
            filenamebuffer.append("-");
        }
        if (StringUtils.isNotBlank(brbid)) {
            filenamebuffer.append(brbid);
            filenamebuffer.append("栋-");
        }
        filenamebuffer.append("学生留校信息");
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
        String brarea = (String) request.getSession().getAttribute("brarea");
        String brbid = (String) request.getSession().getAttribute("brbid");
        JSONObject data = dormService.getStayInfoEchartsData(brarea, brbid);
        System.out.println(JSON.toJSONString(data));
        return JSON.toJSONString(data);
    }

    /**
     * 预分配宿舍界面
     *
     * @return
     */
    @RequestMapping(value = "/allocation/pre")
    public String preAllocateDorm() {
        return "/dm/pre-allocate";
    }

    /**
     * 查找空余寝室
     *
     * @param biVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/room/list")
    public DataGridViewResult getFreeRoomList(BuildRoomInfoVo biVo) {
        System.out.println(biVo);
        // 设置分页信息
        PageHelper.startPage(biVo.getPage(), biVo.getLimit());
        // 查询
        List<BuildRoomInfo> list = dormService.findFreeRoomListByPage(biVo);
        // 创建分页对象
        PageInfo<BuildRoomInfo> pageInfo = new PageInfo<BuildRoomInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    /**
     * 查找未分配寝室的学生
     *
     * @param siVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/student/list")
    public DataGridViewResult getNotAllocateStudentList(StudentInfoVo siVo) {
        System.out.println(siVo);
        // 设置分页信息
        PageHelper.startPage(siVo.getPage(), siVo.getLimit());
        // 查询
        List<StudentInfo> list = dormService.findNotAllocateStudentListByPage(siVo);
        // 创建分页对象
        PageInfo<StudentInfo> pageInfo = new PageInfo<StudentInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }

    @ResponseBody
    @RequestMapping(value = "/allocation/doAllocate")
    public String doAllocate(HttpServletRequest request) {
        HashMap<String, Object> map = new HashMap<>();
        String room = request.getParameter("room");
        String student = request.getParameter("student");
        System.out.println(room);
        System.out.println(student);
        map.put("msg", "接受到数据");
        return JSON.toJSONString(map);
    }

    /**
     * 判断床位数够不够
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/allocation/isEnough")
    public String judgeIsEnough() {
        HashMap<String, Object> map = new HashMap<>();
        boolean enough = dormService.judgeIsEnough();
        if (enough) {
            map.put("success", true);
        } else {
            map.put("success", false);
            map.put("msg", "床位数不够，请先添加空余宿舍信息！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 全部分配
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/allocation/assignAll")
    public String assignAll() {
        HashMap<String, Object> map = new HashMap<>();
        boolean success = dormService.doAssignAll();
        if (success) {
            map.put("success", true);
            map.put("msg", "分配完毕，分配结果显示在当前页面下方。");
        } else {
            map.put("success", false);
            map.put("msg", "分配失败！");
        }
        return JSON.toJSONString(map);
    }

    /**
     * 显示分配结果
     *
     * @param aiVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/allocation/result")
    public DataGridViewResult viewAllocateResult(AllocationInfoVo aiVo) {
        // 设置分页信息
        PageHelper.startPage(aiVo.getPage(), aiVo.getLimit());
        // 查询
        List<AllocationInfo> list = dormService.viewAllocateResult(aiVo);
        // 创建分页对象
        PageInfo<AllocationInfo> pageInfo = new PageInfo<AllocationInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }


    /**
     * 宿舍分配列表
     *
     * @return
     */
    @RequestMapping(value = "/allocation/list")
    public String allocationList() {
        return "/dm/allocation-list";
    }


    /**
     * 查询宿舍分配信息
     * 查询条件：专业/年级
     *
     * @param aiVo
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/allocation/info")
    public DataGridViewResult findAllocationInfoList(AllocationInfoVo aiVo, HttpServletRequest request) {
        String brarea = (String) request.getSession().getAttribute("brarea");
        String brbid = (String) request.getSession().getAttribute("brbid");
        if (StringUtils.isNotBlank(brarea)) {
            aiVo.setBrarea(brarea);
        }
        if (StringUtils.isNotBlank(brbid)) {
            aiVo.setBrbid(brbid);
        }
        System.out.println(aiVo);
        // 设置分页信息
        PageHelper.startPage(aiVo.getPage(), aiVo.getLimit());
        // 查询
        List<AllocationInfo> list = dormService.findAllocationInfoListByPage(aiVo);
        // 创建分页对象
        PageInfo<AllocationInfo> pageInfo = new PageInfo<AllocationInfo>(list);
        // 按接口要求返回数据
        DataGridViewResult data = new DataGridViewResult(pageInfo.getTotal(), pageInfo.getList());
        return data;
    }


    /**
     * 导出学生宿舍分配信息
     *
     * @param request
     * @param response
     */
    @ResponseBody
    @RequestMapping(value = "/allocation/export.action")
    public void exportAllocationInfoToExcel(HttpServletRequest request, HttpServletResponse response) {
        response.reset();// 清除缓存
        String brarea = (String) request.getSession().getAttribute("brarea");
        String brbid = (String) request.getSession().getAttribute("brbid");
        // 查找宿舍分配信息
        List<AllocationInfo> list = dormService.exportAllocationInfo(brarea, brbid);

        // 拼接excel表名
        StringBuffer filenamebuffer = new StringBuffer();
        if (StringUtils.isNotBlank(brarea)) {
            filenamebuffer.append(brarea);
            filenamebuffer.append("-");
        }
        if (StringUtils.isNotBlank(brbid)) {
            filenamebuffer.append(brbid);
            filenamebuffer.append("栋-");
        }
        filenamebuffer.append("学生宿舍分配名单");
        String filename = filenamebuffer.toString();
        try {
            ExcelUtils.writeExcel(filename, response, list, AllocationInfo.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
