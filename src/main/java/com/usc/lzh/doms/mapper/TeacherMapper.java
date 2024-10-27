package com.usc.lzh.doms.mapper;

import com.usc.lzh.doms.entity.*;
import com.usc.lzh.doms.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TeacherMapper {
    /**
     * 查询宿舍分配信息
     *
     * @param aiVo 查询条件(专业/年级)
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
     * 批量插入学生信息
     *
     * @param list 学生列表
     * @return
     */
    public int batchInsert(List<StudentInfo> list);

    /**
     * 查找卫生检查记录信息
     *
     * @param ciVo
     * @return
     */
    public List<CleanInfoVo> findCleanInfoListByPage(CleanInfoVo ciVo);

    /**
     * 查找学生的离校信息
     *
     * @param liVo
     * @return
     */
    public List<LeaveInfo> findLeaveInfoListByPage(LeaveInfoVo liVo);

    /**
     * 批量删除学生离校登记记录
     *
     * @param list
     */
    public void batchDeleteLeaveInfo(List<Integer> list);

    /**
     * 导出学生登记信息
     *
     * @param studept
     * @param stugrade
     * @return
     */
    public List<LeaveInfo> exportLeaveInfo(String studept, String stugrade);

    /**
     * 获取指定去向类型的总人数
     *
     * @param studept
     * @param stugrade
     * @param got
     * @return
     */
    public Integer getLeaveGotCount(@Param("studept") String studept, @Param("stugrade") String stugrade, @Param("got") Integer got);

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
     * 查找留校学生所处的宿舍区
     *
     * @return
     */
    public List<String> getStayInfoOnWhere();


    /**
     * 查找指定宿舍区的留校学生人数
     *
     * @param brarea
     * @param studept
     * @param stugrade
     * @return
     */
    public Integer getStayInfoOnBrareaCount(String brarea, String studept, String stugrade);

    /**
     * 查找学生返校信息
     *
     * @param bsVo
     * @return
     */
    public List<BackToScInfo> findBackToScInfoListByPage(BackToScInfoVo bsVo);

    /**
     * 获取按时返校与延迟返校的统计数据
     *
     * @param delay
     * @param studept
     * @param stugrade
     * @return
     */
    public Integer getDelayOrNotCount(String studept, String stugrade, String delay);
}
