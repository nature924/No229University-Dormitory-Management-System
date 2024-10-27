package com.usc.lzh.doms.service;

import com.usc.lzh.doms.entity.*;
import com.usc.lzh.doms.vo.*;

import java.util.List;
import java.util.Map;


public interface StudentService {
    /**
     * 查找我的宿舍的卫生检查记录
     *
     * @param ciVo 学号
     * @return
     */
    public List<CleanInfo> findMyCleanInfoListByPage(CleanInfoVo ciVo);

    /**
     * 查找我的宿舍的报修记录
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
     * 查找留言信息
     *
     * @param uid
     * @param start
     * @param size
     * @param type
     * @return
     */
    public List<MessageBoard> findMessageListByPage(String uid, Integer start, Integer size, Integer type);

    /**
     * 获取留言信息的总条数
     *
     * @param type
     * @return
     */
    public Integer findMessageCount(Integer type);

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
     * @return
     */
    public boolean deleteMessage(List<Integer> list);

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
     * 查找自己的留校申请
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
     * 提交填写的返校信息
     *
     * @param bs
     * @return
     */
    public int addMyBackInfo(BackToScInfo bs);

    /**
     * 修改返校信息
     *
     * @param bs
     * @return
     */
    public int updateMyBackInfo(BackToScInfo bs);

    /**
     * 查看自己宿舍的信息
     *
     * @param aiVo
     * @return
     */
    public List<AllocationInfo> findMyDormitoryInfoListByPage(AllocationInfoVo aiVo);

    /**
     * 判断是否已经选择床位了
     *
     * @param stuid
     * @return
     */
    public boolean isChooseBed(String stuid);

    /**
     * 提交选择的床位号
     *
     * @param stuid
     * @param bed
     * @return
     */
    public int chooseBed(String stuid, Integer bed);

    /**
     * 查找已经选择了的床位
     *
     * @param stuid
     * @return
     */
    public List<Integer> findAlreadyChooseBeds(String stuid);
}
