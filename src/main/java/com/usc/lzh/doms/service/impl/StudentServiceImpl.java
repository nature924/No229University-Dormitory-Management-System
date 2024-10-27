package com.usc.lzh.doms.service.impl;

import com.usc.lzh.doms.entity.*;
import com.usc.lzh.doms.mapper.StudentMapper;
import com.usc.lzh.doms.service.StudentService;
import com.usc.lzh.doms.utils.MyStringUtil;
import com.usc.lzh.doms.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class StudentServiceImpl implements StudentService {
    @Resource
    private StudentMapper studentMapper;

    private static String brcode = "";

    /**
     * 查找我的宿舍的卫生检查记录
     *
     * @param ciVo 学号
     * @return
     */
    @Override
    public List<CleanInfo> findMyCleanInfoListByPage(CleanInfoVo ciVo) {
        List<CleanInfo> list = studentMapper.findMyCleanInfoListByPage(ciVo);
        return list;
    }

    /**
     * 查找我的宿舍的报修记录
     *
     * @param riVo
     * @return
     */
    @Override
    public List<RepairInfo> findMyRepairInfoListByPage(RepairInfoVo riVo) {
        List<RepairInfo> list = studentMapper.findMyRepairInfoListByPage(riVo);
        return list;
    }

    /**
     * 添加报修记录
     *
     * @param ri
     * @return
     */
    @Override
    public int addRepairInfo(RepairInfo ri) {
        try {
            int result = studentMapper.addRepairInfo(ri);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 修改报修单
     *
     * @param ri
     * @return
     */
    @Override
    public int updateRepairInfo(RepairInfo ri) {
        try {
            int result = studentMapper.updateRepairInfo(ri);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 根据stuid查找用户基本信息
     *
     * @param stuid
     * @return
     */
    @Override
    public StudentInfo findStudentInfoByStuid(String stuid) {
        return studentMapper.findStudentInfoByStuid(stuid);
    }

    /**
     * 更改学生基本信息
     *
     * @param si
     * @return
     */
    @Override
    public int updateStudentInfo(StudentInfo si) {
        try {
            int result = studentMapper.updateStudentInfo(si);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 查找留言信息
     *
     * @param uid
     * @param start
     * @param size
     * @param type
     * @return
     */
    @Override
    public List<MessageBoard> findMessageListByPage(String uid, Integer start, Integer size, Integer type) {
        brcode = studentMapper.findBrcodeByStuid(uid);
        if (StringUtils.isNotBlank(brcode)) {
            String[] split = brcode.split("#");
            // 获取braea
            String brarea = split[0];
            String brbid = split[1];
            String actualBrcode = brarea + "#" + brbid;
            List<MessageBoard> list = studentMapper.findMessageListByPage(start, size, type, actualBrcode, brarea);
            return list;
        } else {
            List<MessageBoard> list = studentMapper.findMessageListByPage(start, size, type, null, null);
            return list;
        }
    }


    /**
     * 获取留言信息的总条数
     *
     * @param type
     * @return
     */
    @Override
    public Integer findMessageCount(Integer type) {
        if (StringUtils.isNotBlank(brcode)) {
            String[] split = brcode.split("#");
            // 获取braea
            String brarea = split[0];
            String brbid = split[1];
            String actualBrcode = brarea + "#" + brbid;
            Integer total = studentMapper.findMessageCount(type, actualBrcode, brarea);
            return total;
        } else {
            Integer total = studentMapper.findMessageCount(type, null, null);
            return total;
        }
    }

    /**
     * 查找我的留言
     *
     * @param mbVo
     * @return
     */
    @Override
    public List<MessageBoard> findMyMessage(MessageBoardVo mbVo) {
        try {
            List<MessageBoard> list = studentMapper.findMyMessage(mbVo);
            for (int i = 0; i < list.size(); i++) {
                // 截取日期
                String time = list.get(i).getTime();
                String date = MyStringUtil.timeTodate(time);
                list.get(i).setTime(date);

                // 解析type
                Integer type = list.get(i).getType();
                list.get(i).setTypeValue(MyStringUtil.mbTypeToValue(type));

                // 解析brcode
                String brcode = list.get(i).getBrcode();
                if (StringUtils.isNotBlank(brcode)) {
                    String[] split = brcode.split("#");
                    switch (split.length) {
                        case 1:
                            list.get(i).setBrarea(MyStringUtil.getBrarea(split[0]));
                            break;
                        case 2:
                        case 3:
                            list.get(i).setBrarea(MyStringUtil.getBrarea(split[0]));
                            list.get(i).setBrbid(split[1]);
                            break;
                    }
                }
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 发布留言
     *
     * @param mb
     * @return
     */
    @Override
    public int addMessage(MessageBoard mb) {
        try {
            // time是当前时间
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            mb.setTime(sdf.format(date));

            // 拼接brcode
            String brcode = MyStringUtil.getBrcode(mb.getBrarea(), mb.getBrbid(), "");
            mb.setBrcode(brcode);

            System.out.println(mb);

            int result = studentMapper.addMessage(mb);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 批量删除留言
     *
     * @param list id数组
     * @return
     */
    @Override
    public boolean deleteMessage(List<Integer> list) {
        try {
            studentMapper.deleteMessage(list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查找我的离校登记记录
     *
     * @param liVo
     * @return
     */
    @Override
    public List<LeaveInfo> findMyLeaveInfoByPage(LeaveInfoVo liVo) {
        try {
            List<LeaveInfo> list = studentMapper.findMyLeaveInfoByPage(liVo);

            for (int i = 0; i < list.size(); i++) {
                // 解析去向的值
                Integer got = list.get(i).getGot();
                list.get(i).setGotValue(MyStringUtil.leaveGotToGotValue(got));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 登记离校信息
     *
     * @param li
     * @return
     */
    @Override
    public int addMyLeaveInfo(LeaveInfo li) {
        try {
            int result = studentMapper.addMyLeaveInfo(li);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 修改离校信息
     *
     * @param li
     * @return
     */
    @Override
    public int updateMyLeaveInfo(LeaveInfo li) {
        try {
            int result = studentMapper.updateMyLeaveInfo(li);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 提交留校申请
     *
     * @param si
     * @return
     */
    @Override
    public int addMyStayInfo(StayInfo si) {
        try {
            int result = studentMapper.addMyStayInfo(si);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 查找自己的留校申请
     *
     * @param siVo
     * @return
     */
    @Override
    public List<StayInfo> findMyStayInfoListByPage(StayInfoVo siVo) {
        try {
            List<StayInfo> list = studentMapper.findMyStayInfoListByPage(siVo);

            for (int i = 0; i < list.size(); i++) {
                // 批准/不批准
                Integer type = list.get(i).getApprovetype();
                list.get(i).setApproveValue(MyStringUtil.stayinTypeToValue(type));
            }
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 修改留校申请信息
     *
     * @param si
     * @return
     */
    @Override
    public int updateMyStayInfo(StayInfo si) {
        try {
            int result = studentMapper.updateMyStayInfo(si);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 查找我的返校信息
     *
     * @param bsVo
     * @return
     */
    @Override
    public List<BackToScInfo> findMyBackInfoByPage(BackToScInfoVo bsVo) {
        try {
            List<BackToScInfo> list = studentMapper.findMyBackInfoByPage(bsVo);
            return list;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 提交填写的返校信息
     *
     * @param bs
     * @return
     */
    @Override
    public int addMyBackInfo(BackToScInfo bs) {
        try {
            int result = studentMapper.addMyBackInfo(bs);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 修改返校信息
     *
     * @param bs
     * @return
     */
    @Override
    public int updateMyBackInfo(BackToScInfo bs) {
        try {
            int result = studentMapper.updateMyBackInfo(bs);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 查看自己宿舍的信息
     *
     * @param aiVo
     * @return
     */
    @Override
    public List<AllocationInfo> findMyDormitoryInfoListByPage(AllocationInfoVo aiVo) {
        List<AllocationInfo> list = studentMapper.findMyDormitoryInfoListByPage(aiVo);
        return list;
    }

    /**
     * 判断是否已经选择床位了
     *
     * @param stuid
     * @return
     */
    @Override
    public boolean isChooseBed(String stuid) {
        int status = studentMapper.getAiStatus(stuid);
        if (status == 1) {
            return true;
        } else {
            return false;
        }
    }


    /**
     * 提交选择的床位号
     *
     * @param stuid
     * @param bed
     * @return
     */
    @Override
    public int chooseBed(String stuid, Integer bed) {
        try {
            int result = studentMapper.chooseBed(stuid, bed);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 查找已经选择了的床位
     *
     * @param stuid
     * @return
     */
    @Override
    public List<Integer> findAlreadyChooseBeds(String stuid) {
        return studentMapper.findAlreadyChooseBeds(stuid);
    }
}
