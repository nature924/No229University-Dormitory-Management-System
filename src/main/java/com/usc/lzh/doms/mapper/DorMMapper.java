package com.usc.lzh.doms.mapper;

import com.usc.lzh.doms.entity.*;
import com.usc.lzh.doms.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface DorMMapper {

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
     * 批量更改报修单编号
     *
     * @param list
     * @param status
     */
    public void batchEditRepairStatus(@Param("list") List<Integer> list, @Param("status") String status);

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
    public int deleteCleanInfo(Integer id);

    /**
     * 批量添加卫生检查记录
     *
     * @param list
     * @return
     */
    public int batchInsertCleanInfo(List<CleanInfo> list);

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
    public int addBuildRoomInfo(List<BuildRoomInfo> list);

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
     * @param list id的数组
     */
    public void deleteMessage(List<Integer> list);

    /**
     * 查找留校信息列表
     *
     * @param stVo
     * @return
     */
    public List<StayInfo> findStayInfoListByPage(StayInfoVo stVo);

    /**
     * 获取留校学生所在的宿舍区
     *
     * @return
     */
    public List<String> getStayInfoBrareas();

    /**
     * 获取指定宿舍区的留校人数
     *
     * @param brarea
     * @param brbid
     * @param approvetype
     * @return
     */
    Integer getStayInfoOnBrareaCount(@Param("brarea") String brarea, @Param("brbid") String brbid, @Param("approvetype") Integer approvetype);

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
     * 批量添加学生宿舍分配信息
     *
     * @param list
     */
    public void batchInsertAllocationInfo(List<AllocationInfo> list);

    /**
     * 更改宿舍空余数和入住人数
     *
     * @param list
     */
    public void batchUpdateBuildRoomInfo(List<BuildRoomInfo> list);


    /**
     * 查看分配结果
     *
     * @param list
     * @param start
     * @param row
     * @return
     */
    public List<AllocationInfo> viewAllocateResult(@Param("list") List<AllocationInfo> list);
//    public List<AllocationInfo> viewAllocateResult(@Param("list") List<AllocationInfo> list, @Param("start") Integer start, @Param("row") Integer row);
}
