package com.usc.lzh.doms.service.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.usc.lzh.doms.entity.*;
import com.usc.lzh.doms.mapper.TeacherMapper;
import com.usc.lzh.doms.service.TeacherService;
import com.usc.lzh.doms.utils.MyStringUtil;
import com.usc.lzh.doms.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class TeacherServiceImpl implements TeacherService {
    @Resource
    private TeacherMapper teacherMapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 查询宿舍分配信息
     *
     * @param aiVo 查询条件(学院/专业)
     * @return
     */
    @Override
    public List<AllocationInfo> findAllocationInfoListByPage(AllocationInfoVo aiVo) {
        List<AllocationInfo> list = teacherMapper.findAllocationInfoListByPage(aiVo);
        for (int i = 0; i < list.size(); i++) {
            String brcode = list.get(i).getBrcode().trim();
            String[] brArr = brcode.split("#");
            list.get(i).setBrarea(MyStringUtil.getBrarea(brArr[0]));
            list.get(i).setBrbid(brArr[1]);
            list.get(i).setBrrid(brArr[2]);
        }
        return list;
    }

    /**
     * 添加宿舍分配信息
     *
     * @param ai 宿舍分配信息
     * @return 影响的结果行
     */
    @Override
    public int addAllocationInfo(AllocationInfo ai) {
        try {
            // 拼接brcode
            String area = ai.getBrarea().trim();
            String bid = ai.getBrbid().trim();
            String rid = ai.getBrrid().trim();
            String brcode = MyStringUtil.getBrcode(area, bid, rid);
            ai.setBrcode(brcode);
            int result = teacherMapper.addAllocationInfo(ai);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 更改宿舍分配信息
     *
     * @param ai
     * @return 影响的结果行
     */
    @Override
    public int updateAllocationInfo(AllocationInfo ai) {
        try {
            // 拼接brcode
            String area = ai.getBrarea().trim();
            String bid = ai.getBrbid().trim();
            String rid = ai.getBrrid().trim();
            String brcode = MyStringUtil.getBrcode(area, bid, rid);
            ai.setBrcode(brcode);
            int result = teacherMapper.updateAllocationInfo(ai);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 根据学号删除宿舍分配信息
     *
     * @param stuid 学号
     * @return
     */
    @Override
    public int deleteAllocationInfo(String stuid) {
        try {
            int result = teacherMapper.deleteAllocationInfo(stuid);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 导出全部宿舍信息
     *
     * @param studept 专业
     * @return
     */
    @Override
    public List<AllocationInfo> exportByDept(String studept, String grade) {
        List<AllocationInfo> list = teacherMapper.exportByDept(studept, grade);
        for (int i = 0; i < list.size(); i++) {
            String brcode = list.get(i).getBrcode().trim();
            String[] brArr = brcode.split("#");
            list.get(i).setBrarea(MyStringUtil.getBrarea(brArr[0]));
            list.get(i).setBrbid(brArr[1]);
            list.get(i).setBrrid(brArr[2]);
        }
        return list;
    }

    /**
     * 根据用户的id查找他负责的专业
     *
     * @param uid 用户id
     * @return
     */
    @Override
    public String findDeptByUid(String uid) {
        return teacherMapper.findDeptByUid(uid);
    }


    /**
     * 查询学生信息
     *
     * @param siVo 查询条件(专业/班级/姓名/学号)
     * @return
     */
    @Override
    public List<StudentInfo> findStudentInfoListByPage(StudentInfoVo siVo) {
        List<StudentInfo> list = teacherMapper.findStudentInfoListByPage(siVo);
        return list;
    }

    /**
     * 添加学生信息
     *
     * @param si
     * @return
     */
    @Override
    public int addStudentInfo(StudentInfo si) {
        try {
            int result = teacherMapper.addStudentInfo(si);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 修改学生信息
     *
     * @param si
     * @return
     */
    @Override
    public int updateStudentInfo(StudentInfo si) {
        try {
            int result = teacherMapper.updateStudentInfo(si);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 删除学生信息
     *
     * @param stuid
     * @return
     */
    @Override
    public int deleteStudentInfo(String stuid) {
        try {
            int result = teacherMapper.deleteStudentInfo(stuid);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }


    /**
     * 批量插入学生信息，配合导入的使用
     *
     * @param list
     * @return 发生异常返回false，无异常返回true
     */
    @Override
    public boolean batchInsert(List<StudentInfo> list) {
        String classPath = "com.usc.lzh.doms.mapper.TeacherMapper.batchInsert";
        // 获取mysql的session并且关闭自动提交
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            // 插入
            sqlSession.insert(classPath, list);
            // 提交
            sqlSession.commit();
            // 防止内存崩溃
            sqlSession.clearCache();
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚
            sqlSession.rollback();
            return false;
        } finally {
            // 关闭
            sqlSession.close();
        }
        return true;
    }


    /**
     * 查找卫生检查记录信息
     *
     * @param ciVo
     * @return
     */
    @Override
    public List<CleanInfoVo> findCleanInfoListByPage(CleanInfoVo ciVo) {
        List<CleanInfoVo> list = teacherMapper.findCleanInfoListByPage(ciVo);
        for (int i = 0; i < list.size(); i++) {
            String time = list.get(i).getTime();
            String date = MyStringUtil.timeTodate(time);
            list.get(i).setTime(date);
        }
        return list;
    }


    /**
     * 查询学生的离校信息
     *
     * @param liVo
     * @return
     */
    @Override
    public List<LeaveInfo> findLeaveInfoListByPage(LeaveInfoVo liVo) {
        List<LeaveInfo> list = teacherMapper.findLeaveInfoListByPage(liVo);
        for (int i = 0; i < list.size(); i++) {
            // 解析去向的值
            Integer got = list.get(i).getGot();
            list.get(i).setGotValue(MyStringUtil.leaveGotToGotValue(got));
        }
        return list;
    }


    /**
     * 批量删除学生离校登记信息
     *
     * @param params
     * @return
     */
    @Override
    public boolean batchDeleteLeaveInfo(String params) {
        try {
            // 获取id数组
            JSONArray jsonArray = JSONArray.parseArray(params);
            List<Integer> list = new ArrayList<>();
            for (int i = 0; i < jsonArray.size(); i++) {
                JSONObject obj = jsonArray.getJSONObject(i);
                Integer id = (Integer) obj.get("id");
                System.out.println(id);
                list.add(id);
            }
            teacherMapper.batchDeleteLeaveInfo(list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 导出学生离校登记信息
     *
     * @param studept
     * @param stugrade
     * @return
     */
    @Override
    public List<LeaveInfo> exportLeaveInfo(String studept, String stugrade) {
        List<LeaveInfo> list = teacherMapper.exportLeaveInfo(studept, stugrade);
        for (int i = 0; i < list.size(); i++) {
            // 解析去向的值
            Integer got = list.get(i).getGot();
            list.get(i).setGotValue(MyStringUtil.leaveGotToGotValue(got));
        }
        return list;
    }

    /**
     * 统计去向数据
     *
     * @param studept
     * @param stugrade
     * @return
     */
    @Override
    public HashMap<String, Object> getLeaveInfoEchartsData(String studept, String stugrade) {
        HashMap<String, Object> map = new HashMap<>();
        Integer stayIn = teacherMapper.getLeaveGotCount(studept, stugrade, 0);
        Integer goHome = teacherMapper.getLeaveGotCount(studept, stugrade, 1);
        Integer goOut = teacherMapper.getLeaveGotCount(studept, stugrade, 2);
        map.put("stayIn", stayIn);
        map.put("goHome", goHome);
        map.put("goOut", goOut);
        return map;
    }

    /**
     * 查找学生留校信息
     *
     * @param siVo
     * @return
     */
    @Override
    public List<StayInfo> findStayInfoListByPage(StayInfoVo siVo) {
        List<StayInfo> list = teacherMapper.findStayInfoListByPage(siVo);
        for (int i = 0; i < list.size(); i++) {
            // 批准/不批准
            Integer type = list.get(i).getApprovetype();
            list.get(i).setApproveValue(MyStringUtil.stayinTypeToValue(type));
        }
        return list;
    }

    /**
     * 审批学生的留校申请
     *
     * @param si
     * @return
     */
    @Override
    public int approveStayInfo(StayInfo si) {
        try {
            int result = teacherMapper.approveStayInfo(si);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 导出留校申请信息
     *
     * @param studept
     * @param stugrade
     * @return
     */
    @Override
    public List<StayInfo> exportStayInfo(String studept, String stugrade) {
        return teacherMapper.exportStayInfo(studept, stugrade);
    }

    /**
     * 获取留校申请中的统计数据
     *
     * @param studept
     * @param stugrade
     * @return
     */
    @Override
    public JSONObject getStayInfoEchartsData(String studept, String stugrade) {
        JSONObject data = new JSONObject();
        JSONArray bsArray = new JSONArray();
        JSONArray countArray = new JSONArray();
        List<String> brareas = teacherMapper.getStayInfoOnWhere();
        for (String brarea : brareas) {
            bsArray.add(brarea);
            Integer count = teacherMapper.getStayInfoOnBrareaCount(brarea, studept, stugrade);
            JSONObject obj = new JSONObject();
            obj.put("name", brarea);
            obj.put("value", count);
            countArray.add(obj);
        }
        data.put("data", bsArray);
        data.put("series", countArray);
        return data;
    }

    /**
     * 查找学生返校信息
     *
     * @param bsVo
     * @return
     */
    @Override
    public List<BackToScInfo> findBackToScInfoListByPage(BackToScInfoVo bsVo) {
        return teacherMapper.findBackToScInfoListByPage(bsVo);
    }

    /**
     * 导出学生返校登记信息
     *
     * @param studept
     * @param stugrade
     * @return
     */
    @Override
    public List<BackToScInfo> exportBackToScInfo(String studept, String stugrade) {
        BackToScInfoVo bsVo = new BackToScInfoVo();
        bsVo.setStudept(studept);
        bsVo.setStugrade(stugrade);
        List<BackToScInfo> list = teacherMapper.findBackToScInfoListByPage(bsVo);
        return list;
    }

    /**
     * 获取返校登记的统计数据
     *
     * @param studept
     * @param stugrade
     * @return
     */
    public JSONObject getBackToScInfoEchartsData(String studept, String stugrade) {
        Integer delay = teacherMapper.getDelayOrNotCount(studept, stugrade, "是");
        Integer notDelay = teacherMapper.getDelayOrNotCount(studept, stugrade, "否");
        JSONObject obj = new JSONObject();
        obj.put("delay", delay);
        obj.put("notDelay", notDelay);
        return obj;
    }
}
