package com.usc.lzh.doms.service;

import com.alibaba.fastjson.JSONObject;
import com.usc.lzh.doms.entity.*;
import com.usc.lzh.doms.vo.*;

import java.util.HashMap;
import java.util.List;

public interface TeacherService {
    /**
     * 查询宿舍分配信息
     *
     * @param aiVo 查询条件(学院/专业)
     * @return
     */
    public List<AllocationInfo> findAllocationInfoListByPage(AllocationInfoVo aiVo);

    /**
     * 添加宿舍分配信息
     *
     * @param ai 宿舍分配信息
     * @return 影响的结果行
     */
    public int addAllocationInfo(AllocationInfo ai);

    /**
     * 更改宿舍分配信息
     *
     * @param ai
     * @return 影响的结果行
     */
    public int updateAllocationInfo(AllocationInfo ai);

    /**
     * 根据学号删除宿舍分配信息
     *
     * @param stuid 学号
     * @return
     */
    public int deleteAllocationInfo(String stuid);

    /**
     * 导出全部宿舍信息
     *
     * @param studept 专业
     * @return
     */
    public List<AllocationInfo> exportByDept(String studept, String grade);

    /**
     * 根据用户的id查找他负责的专业
     *
     * @param uid 用户id
     * @return
     */
    public String findDeptByUid(String uid);

    /**
     * 查询学生信息
     *
     * @param siVo 查询条件(专业/班级/姓名/学号)
     * @return
     */
    public List<StudentInfo> findStudentInfoListByPage(StudentInfoVo siVo);

    /**
     * 添加学生信息
     *
     * @param si
     * @return
     */
    public int addStudentInfo(StudentInfo si);

    /**
     * 修改学生信息
     *
     * @param si
     * @return
     */
    public int updateStudentInfo(StudentInfo si);

    /**
     * 删除学生信息
     *
     * @param stuid
     * @return
     */
    public int deleteStudentInfo(String stuid);


    /**
     * 批量插入学生信息，配合导入的使用
     *
     * @param list
     * @return
     */
    public boolean batchInsert(List<StudentInfo> list);

    /**
     * 查找卫生检查记录信息
     *
     * @param ciVo
     * @return
     */
    public List<CleanInfoVo> findCleanInfoListByPage(CleanInfoVo ciVo);

    /**
     * 查询学生的离校信息
     *
     * @param liVo
     * @return
     */
    public List<LeaveInfo> findLeaveInfoListByPage(LeaveInfoVo liVo);

    /**
     * 批量删除学生离校登记信息
     *
     * @param params
     * @return
     */
    public boolean batchDeleteLeaveInfo(String params);

    /**
     * 导出学生离校登记信息
     *
     * @param studept
     * @param stugrade
     * @return
     */
    public List<LeaveInfo> exportLeaveInfo(String studept, String stugrade);

    /**
     * 统计去向数据
     *
     * @param studept
     * @param stugrade
     * @return
     */
    public HashMap<String, Object> getLeaveInfoEchartsData(String studept, String stugrade);

    /**
     * 查找学生留校信息
     *
     * @param siVo
     * @return
     */
    public List<StayInfo> findStayInfoListByPage(StayInfoVo siVo);

    /**
     * 审批学生的留校申请
     *
     * @param si
     * @return
     */
    public int approveStayInfo(StayInfo si);

    /**
     * 导出留校申请信息
     *
     * @param studept
     * @param stugrade
     * @return
     */
    public List<StayInfo> exportStayInfo(String studept, String stugrade);

    /**
     * 获取留校申请中的统计数据
     *
     * @param studept
     * @param stugrade
     * @return
     */
    public JSONObject getStayInfoEchartsData(String studept, String stugrade);

    /**
     * 查找学生返校信息
     *
     * @param bsVo
     * @return
     */
    public List<BackToScInfo> findBackToScInfoListByPage(BackToScInfoVo bsVo);

    /**
     * 导出学生返校登记信息
     *
     * @param studept
     * @param stugrade
     * @return
     */
    public List<BackToScInfo> exportBackToScInfo(String studept, String stugrade);

    /**
     * 获取返校登记的统计数据
     *
     * @param studept
     * @param stugrade
     * @return
     */
    public JSONObject getBackToScInfoEchartsData(String studept, String stugrade);
}
