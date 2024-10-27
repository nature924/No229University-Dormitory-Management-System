package com.usc.lzh.doms.service;

import com.alibaba.fastjson.JSONObject;
import com.usc.lzh.doms.entity.*;
import com.usc.lzh.doms.vo.*;

import java.util.List;

public interface DorMService {

    /**
     * 查找报修信息
     *
     * @param riVo 分页查询的参数，负责的楼栋编号
     * @return
     */
    public List<RepairInfo> findRepairInfoListByPage(RepairInfoVo riVo);

    /**
     * 导出报修信息
     *
     * @param brcode 宿舍楼编号
     * @param status 报修状态
     * @return
     */
    public List<RepairInfo> exportRepairInfo(String brcode, String status);

    /**
     * 批量更改报修状态
     *
     * @param params ids和status
     */
    public boolean batchEditRepairStatus(String params);

    /**
     * 查询卫生检查信息
     *
     * @param ciVo
     * @return
     */
    public List<CleanInfo> findCleanInfoListByPage(CleanInfoVo ciVo);

    /**
     * 更改卫生检查信息
     *
     * @param ci
     * @return
     */
    public int updateCleanInfo(CleanInfo ci);

    /**
     * 删除卫生检查记录
     *
     * @param id
     * @return
     */
    public int deleteCleanInfo(String id);

    /**
     * 批量添加卫生检查记录
     *
     * @param params  卫生检查信息的json数据
     * @param checker 检查人
     * @return
     */
    public boolean batchInsertCleanInfo(String params, String checker);

    /**
     * 查找宿舍信息
     *
     * @param biVo
     * @return
     */
    public List<RepairInfo> findBuildRoomInfoListByPage(BuildRoomInfoVo biVo);

    /**
     * 修改宿舍信息
     *
     * @param bi
     * @return
     */
    public int updateBuildRoomInfo(BuildRoomInfo bi);

    /**
     * 删除宿舍信息
     *
     * @param brcode 宿舍编号
     * @return
     */
    public int deleteBuildRoomInfo(String brcode);

    /**
     * 添加宿舍信息
     *
     * @param list
     * @return
     */
    public boolean addBuildRoomInfo(List<BuildRoomInfo> list);

    /**
     * 查看自己发布的公告信息
     *
     * @param mbVo
     * @return
     */
    public List<MessageBoard> findMessageListByPage(MessageBoardVo mbVo);

    /**
     * 添加公告信息
     *
     * @param mb
     * @return
     */
    public int addMessage(MessageBoard mb);

    /**
     * 修改公告信息
     *
     * @param mb
     * @return
     */
    public int updateMessage(MessageBoard mb);

    /**
     * 批量删除公告/失物招领信息
     *
     * @param list id数组
     * @return
     */
    public boolean deleteMessage(List<Integer> list);

    /**
     * 查找留校信息列表
     *
     * @param stVo
     * @return
     */
    public List<StayInfo> findStayInfoListByPage(StayInfoVo stVo);

    /**
     * 导出学生留校信息
     *
     * @param brarea
     * @param brbid
     * @return
     */
    public List<StayInfo> exportStayInfo(String brarea, String brbid);

    /**
     * 获取留校学生的统计数据
     *
     * @param brarea
     * @param brbid
     * @return
     */
    public JSONObject getStayInfoEchartsData(String brarea, String brbid);

    /**
     * 查找宿舍分配信息
     *
     * @param aiVo 按专业、班级、宿舍区、楼栋进行查找
     * @return
     */
    public List<AllocationInfo> findAllocationInfoListByPage(AllocationInfoVo aiVo);

    /**
     * 导出宿舍分配信息
     *
     * @param brarea
     * @param brbid
     * @return
     */
    public List<AllocationInfo> exportAllocationInfo(String brarea, String brbid);

    /**
     * 查找空余寝室
     *
     * @param biVo
     * @return
     */
    public List<BuildRoomInfo> findFreeRoomListByPage(BuildRoomInfoVo biVo);

    /**
     * 查找未分配寝室的学生
     *
     * @param siVo
     * @return
     */
    public List<StudentInfo> findNotAllocateStudentListByPage(StudentInfoVo siVo);

    /**
     * 判断床位够不够
     *
     * @return
     */
    public boolean judgeIsEnough();


    /**
     * 分配宿舍（全部分配）
     *
     * @return
     */
    public boolean doAssignAll();

    /**
     * 显示分配结果
     *
     * @param aiVo
     * @return
     */
    public List<AllocationInfo> viewAllocateResult(AllocationInfoVo aiVo);
}
