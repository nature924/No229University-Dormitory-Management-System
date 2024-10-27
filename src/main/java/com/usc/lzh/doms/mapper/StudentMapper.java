package com.usc.lzh.doms.mapper;

import com.usc.lzh.doms.entity.*;
import com.usc.lzh.doms.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentMapper {
    /**
     * 查找我的宿舍的卫生检查记录
     *
     * @param ciVo 学号
     * @return
     */
    public List<CleanInfo> findMyCleanInfoListByPage(CleanInfoVo ciVo);

    /**
     * 查找我的宿舍报修记录
     *
     * @param riVo
     * @return
     */
    public List<RepairInfo> findMyRepairInfoListByPage(RepairInfoVo riVo);

    /**
     * 添加报修记录
     *
     * @param ri
     * @return
     */
    public int addRepairInfo(RepairInfo ri);

    /**
     * 修改报修单
     *
     * @param ri
     * @return
     */
    public int updateRepairInfo(RepairInfo ri);

    /**
     * 根据stuid查找用户基本信息
     *
     * @param stuid
     * @return
     */
    public StudentInfo findStudentInfoByStuid(String stuid);

    /**
     * 更改学生基本信息
     *
     * @param si
     * @return
     */
    public int updateStudentInfo(StudentInfo si);

    /**
     * 根据学号查找宿舍区
     *
     * @param uid 学号
     * @return
     */
    public String findBrcodeByStuid(String uid);

    /**
     * 查找留言
     *
     * @param start  分页的起始行数
     * @param size   一页的条数
     * @param type   留言类型
     * @param brcode
     * @param brarea
     * @return
     */
    public List<MessageBoard> findMessageListByPage(@Param("start") Integer start, @Param("size") Integer size, @Param("type") Integer type, @Param("brcode") String brcode, @Param("brarea") String brarea);

    /**
     * 留言的总条数
     *
     * @param type
     * @param actualBrcode
     * @param brarea
     * @return
     */
    public Integer findMessageCount(@Param("type") Integer type, @Param("brcode") String actualBrcode, @Param("brarea") String brarea);

    /**
     * 查找我的留言
     *
     * @param mbVo
     * @return
     */
    public List<MessageBoard> findMyMessage(MessageBoardVo mbVo);

    /**
     * 发布留言
     *
     * @param mb
     * @return
     */
    public int addMessage(MessageBoard mb);

    /**
     * 批量删除留言
     *
     * @param list
     */
    public void deleteMessage(List<Integer> list);

    /**
     * 查找我的离校登记记录
     *
     * @param liVo
     * @return
     */
    public List<LeaveInfo> findMyLeaveInfoByPage(LeaveInfoVo liVo);

    /**
     * 登记离校信息
     *
     * @param li
     * @return
     */
    public int addMyLeaveInfo(LeaveInfo li);

    /**
     * 修改离校信息
     *
     * @param li
     * @return
     */
    public int updateMyLeaveInfo(LeaveInfo li);

    /**
     * 提交留校申请
     *
     * @param si
     * @return
     */
    public int addMyStayInfo(StayInfo si);

    /**
     * 查看自己的留校申请
     *
     * @param siVo
     * @return
     */
    public List<StayInfo> findMyStayInfoListByPage(StayInfoVo siVo);

    /**
     * 修改留校申请信息
     *
     * @param si
     * @return
     */
    public int updateMyStayInfo(StayInfo si);

    /**
     * 查找我的返校信息
     *
     * @param bsVo
     * @return
     */
    public List<BackToScInfo> findMyBackInfoByPage(BackToScInfoVo bsVo);

    /**
     * 修改返校信息
     *
     * @param bs
     * @return
     */
    public int updateMyBackInfo(BackToScInfo bs);

    /**
     * 提交填写的返校信息
     *
     * @param bs
     * @return
     */
    public int addMyBackInfo(BackToScInfo bs);

    /**
     * 查看自己宿舍的信息
     *
     * @param aiVo
     * @return
     */
    public List<AllocationInfo> findMyDormitoryInfoListByPage(AllocationInfoVo aiVo);

    /**
     * 获取床位的选择状态
     *
     * @param stuid
     * @return
     */
    public int getAiStatus(String stuid);

    /**
     * 提交选择的床位号
     *
     * @param stuid
     * @param bed
     * @return
     */
    public int chooseBed(@Param("stuid") String stuid, @Param("bed") Integer bed);

    /**
     * 查找已经选择了的床位
     *
     * @param stuid
     * @return
     */
    public List<Integer> findAlreadyChooseBeds(String stuid);
}
