package com.usc.lzh.doms.service.impl;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.usc.lzh.doms.entity.*;
import com.usc.lzh.doms.mapper.DorMMapper;
import com.usc.lzh.doms.service.DorMService;
import com.usc.lzh.doms.utils.MyStringUtil;
import com.usc.lzh.doms.vo.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.xmlbeans.impl.xb.xsdschema.All;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DorMServiceImpl implements DorMService {

    @Resource
    private DorMMapper dormMapper;

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 查找报修信息
     *
     * @param riVo 分页查询的参数，负责的楼栋编号
     * @return
     */
    @Override
    public List<RepairInfo> findRepairInfoListByPage(RepairInfoVo riVo) {
        List<RepairInfo> list = dormMapper.findRepairInfoListByPage(riVo);
        return dealString(list);
    }

    // 解析brcode填充brarea/brbid/brrid和格式化日期字符串
    private List<RepairInfo> dealString(List<RepairInfo> list) {
        for (int i = 0; i < list.size(); i++) {
            String brcode = list.get(i).getBrcode().trim();
            String subtime = list.get(i).getSubtime();
            // 截取空格前的字符串，使日期格式为yy-MM-dd
            String date = MyStringUtil.timeTodate(subtime);
            list.get(i).setSubtime(date);

            String[] brArr = brcode.split("#");
            list.get(i).setBrarea(MyStringUtil.getBrarea(brArr[0]));
            list.get(i).setBrbid(brArr[1]);
            list.get(i).setBrrid(brArr[2]);
        }
        return list;
    }


    /**
     * 导出报修信息
     *
     * @param brcode     宿舍楼编号
     * @param statusCode 报修状态
     * @return
     */
    public List<RepairInfo> exportRepairInfo(String brcode, String statusCode) {
        String status = transStatusCode(statusCode);
        List<RepairInfo> list = dormMapper.exportRepairInfo(brcode, status);
        return dealString(list);
    }

    // 转换statusCode为数据库中的status
    private String transStatusCode(String status) {
        if (status != null && !status.equals("")) {
            Integer statusCode = Integer.parseInt(status.trim());
            switch (statusCode) {
                case 1:
                    status = "已处理";
                    break;
                case 2:
                    status = "未处理";
                    break;
                case 3:
                    status = "正在处理";
                    break;
            }
        } else {
            status = "";
        }
        return status;
    }

    /**
     * 批量更改报修状态
     *
     * @param params ids和status
     */
    @Override
    public boolean batchEditRepairStatus(String params) {
        try {
            // 将前端json数据解析出来
            JSONArray jsonArray = JSONArray.parseArray(params);
            JSONObject jsonObject = jsonArray.getJSONObject(0);

            String statusCode = (String) jsonObject.get("status");
            String status = transStatusCode(statusCode);
            // ids为要更新状态的报修单编号
            String ids = jsonObject.get("ids").toString();
            // 去掉两边的[]
            ids = ids.substring(1, ids.length() - 1);
            // 转为String字符串
            String[] idsArray = ids.split(",");
            // 字符数组转为int数组
            int[] array = Arrays.stream(idsArray).mapToInt(Integer::parseInt).toArray();
            // int数组转为List，装箱
            List<Integer> list = Arrays.stream(array).boxed().collect(Collectors.toList());
            dormMapper.batchEditRepairStatus(list, status);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 查询卫生检查信息
     *
     * @param ciVo
     * @return
     */
    @Override
    public List<CleanInfo> findCleanInfoListByPage(CleanInfoVo ciVo) {
        List<CleanInfo> list = dormMapper.findCleanInfoListByPage(ciVo);
        for (int i = 0; i < list.size(); i++) {
            String brcode = list.get(i).getBrcode().trim();
            String time = list.get(i).getTime();
            // 截取空格前的字符串，使日期格式为yy-MM-dd
            String date = MyStringUtil.timeTodate(time);
            list.get(i).setTime(date);

            String[] brArr = brcode.split("#");
            list.get(i).setBrarea(MyStringUtil.getBrarea(brArr[0]));
            list.get(i).setBrbid(brArr[1]);
            list.get(i).setBrrid(brArr[2]);
        }
        return list;
    }

    /**
     * 更改卫生检查信息
     *
     * @param ci
     * @return
     */
    @Override
    public int updateCleanInfo(CleanInfo ci) {
        try {
            // 如果宿舍号改了
//            String brcode = ci.getBrcode();
//            if (StringUtils.isNotBlank(brcode)){
//                String brrid = brcode.split("#")[2];
//                ci.setBrbid(brrid);
//            }
            int result = dormMapper.updateCleanInfo(ci);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 删除卫生检查记录
     *
     * @param id
     * @return
     */
    @Override
    public int deleteCleanInfo(String id) {
        try {
            int actualId = Integer.parseInt(id);
            int result = dormMapper.deleteCleanInfo(actualId);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 批量添加卫生检查记录
     *
     * @param params  卫生检查信息的json数据
     * @param checker 检查人
     * @return
     */
    @Override
    public boolean batchInsertCleanInfo(String params, String checker) {
        String classPath = "com.usc.lzh.doms.mapper.DorMMapper.batchInsertCleanInfo";
        // 获取mysql的session并且关闭自动提交
        SqlSession sqlSession = sqlSessionTemplate.getSqlSessionFactory().openSession(ExecutorType.BATCH, false);
        try {
            List<CleanInfo> list = dealCleanInfoAddParams(params, checker);
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

    private List<CleanInfo> dealCleanInfoAddParams(String params, String checker) throws Exception {
        List<CleanInfo> list = new ArrayList<>();
        if (StringUtils.isNotBlank(params)) {
            // json数据转为JSONArray对象
            JSONArray jsonArray = JSONArray.parseArray(params);
            // 遍历json数组，将json对象转为CleanInfo对象并且存到list中
            for (int i = 0; i < jsonArray.size(); i++) {
                CleanInfo ci = new CleanInfo();
                JSONObject obj = jsonArray.getJSONObject(i);
                String brarea = obj.get("brarea").toString().trim();
                String brbid = obj.get("brbid").toString().trim();
                String brrid = obj.get("brrid").toString().trim();
                String brcode = MyStringUtil.getBrcode(brarea, brbid, brrid);
                ci.setBrarea(brarea);
                ci.setBrbid(brbid);
                ci.setBrrid(brrid);
                ci.setBrcode(brcode);
                ci.setTime(obj.get("time").toString().trim());
                String grade = obj.get("grade").toString().trim();
                if (StringUtils.isNotBlank(grade)) {
                    ci.setGrade(Integer.parseInt(grade));
                }
                ci.setContent(obj.get("content").toString().trim());
                ci.setChecker(checker);
                list.add(ci);
            }
        }
        return list;
    }

    /**
     * 查找宿舍信息
     *
     * @param biVo
     * @return
     */
    @Override
    public List<RepairInfo> findBuildRoomInfoListByPage(BuildRoomInfoVo biVo) {
        List<RepairInfo> list = dormMapper.findBuildRoomInfoListByPage(biVo);
        return list;
    }


    /**
     * 修改宿舍信息
     *
     * @param bi
     * @return
     */
    @Override
    public int updateBuildRoomInfo(BuildRoomInfo bi) {
        try {
            int result = dormMapper.updateBuildRoomInfo(bi);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 删除宿舍信息
     *
     * @param brcode 宿舍编号
     * @return
     */
    @Override
    public int deleteBuildRoomInfo(String brcode) {
        try {
            int result = dormMapper.deleteBuildRoomInfo(brcode);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 添加宿舍信息
     *
     * @param list
     * @return
     */
    @Override
    public boolean addBuildRoomInfo(List<BuildRoomInfo> list) {
        String classPath = "com.usc.lzh.doms.mapper.DorMMapper.addBuildRoomInfo";
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
     * 查看自己发布的公告/失物招领信息
     *
     * @param mbVo
     * @return
     */
    @Override
    public List<MessageBoard> findMessageListByPage(MessageBoardVo mbVo) {
        System.out.println(mbVo.getType());
        List<MessageBoard> list = dormMapper.findMessageListByPage(mbVo);
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
            System.out.println(brcode);
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
    }

    /**
     * 添加公告信息
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

            int result = dormMapper.addMessage(mb);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 修改公告信息
     *
     * @param mb
     * @return
     */
    @Override
    public int updateMessage(MessageBoard mb) {
        try {
            int result = dormMapper.updateMessage(mb);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 批量删除公告/失物招领信息
     *
     * @param list id数组
     * @return
     */
    @Override
    public boolean deleteMessage(List<Integer> list) {
        try {
            dormMapper.deleteMessage(list);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 查找留校信息列表
     *
     * @param stVo
     * @return
     */
    @Override
    public List<StayInfo> findStayInfoListByPage(StayInfoVo stVo) {
        stVo.setApprovetype(1);
        List<StayInfo> list = dormMapper.findStayInfoListByPage(stVo);
        return list;
    }

    /**
     * 导出学生留校信息
     *
     * @param brarea
     * @param brbid
     * @return
     */
    @Override
    public List<StayInfo> exportStayInfo(String brarea, String brbid) {
        StayInfoVo stVo = new StayInfoVo();
        stVo.setBrbid(brbid);
        stVo.setBrarea(brarea);
        stVo.setApprovetype(1);
        List<StayInfo> list = dormMapper.findStayInfoListByPage(stVo);
        return list;
    }

    /**
     * 获取留校学生的统计数据
     *
     * @param brarea
     * @param brbid
     * @return
     */
    @Override
    public JSONObject getStayInfoEchartsData(String brarea, String brbid) {
        // 最后返回的数据
        JSONObject data = new JSONObject();
        // 宿舍区
        JSONArray bsArray = new JSONArray();
        // 宿舍区及人数
        JSONArray countArray = new JSONArray();
        List<String> brareas = new ArrayList<>();
        if (StringUtils.isNotBlank(brarea)) {
            brareas.add(brarea);
        } else {
            brareas = dormMapper.getStayInfoBrareas();
        }
        for (String item : brareas) {
            bsArray.add(item);
            Integer count = dormMapper.getStayInfoOnBrareaCount(item, brbid, 1);
            JSONObject obj = new JSONObject();
            obj.put("name", item);
            obj.put("value", count);
            countArray.add(obj);
        }
        data.put("data", bsArray);
        data.put("series", countArray);
        return data;
    }

    /**
     * 查找宿舍分配信息
     *
     * @param aiVo 按专业、班级、宿舍区、楼栋进行查找
     * @return
     */
    @Override
    public List<AllocationInfo> findAllocationInfoListByPage(AllocationInfoVo aiVo) {
        List<AllocationInfo> list = dormMapper.findAllocationInfoListByPage(aiVo);
        return list;
    }

    /**
     * 导出宿舍分配信息
     *
     * @param brarea
     * @param brbid
     * @return
     */
    public List<AllocationInfo> exportAllocationInfo(String brarea, String brbid) {
        List<AllocationInfo> list = dormMapper.exportAllocationInfo(brarea, brbid);
        return list;
    }

    /**
     * 查找空余寝室
     *
     * @param biVo
     * @return
     */
    public List<BuildRoomInfo> findFreeRoomListByPage(BuildRoomInfoVo biVo) {
        return dormMapper.findFreeRoomListByPage(biVo);
    }

    /**
     * 查找未分配寝室的学生
     *
     * @param siVo
     * @return
     */
    public List<StudentInfo> findNotAllocateStudentListByPage(StudentInfoVo siVo) {
        return dormMapper.findNotAllocateStudentListByPage(siVo);
    }

    private List<StudentInfo> MsiList = null;//男生
    private List<StudentInfo> FsiList = null;//女生
    private List<BuildRoomInfo> MbiList = null;//男生寝室
    private List<BuildRoomInfo> FbiList = null;//女生寝室
    private List<AllocationInfo> aiList = new ArrayList<>();
    private List<BuildRoomInfo> updateList = new ArrayList<>();

    /**
     * 判断床位够不够
     *
     * @return
     */
    public boolean judgeIsEnough() {
        initList();//初始化列表
        int mbed = 0;//男寝床位
        int fbed = 0;//女寝床位
        for (int i = 0; i < MbiList.size(); i++) {
            mbed += MbiList.get(i).getFree();
        }
        for (int i = 0; i < FbiList.size(); i++) {
            fbed += FbiList.get(i).getFree();
        }
        int mstucount = MsiList.size();//男生人数
        int fstucount = FsiList.size();//女生人数
        System.out.println(mbed + "--" + mstucount);
        System.out.println(fbed + "--" + fstucount);
        if (mbed >= mstucount && fbed >= fstucount) {
            return true;
        }
        return false;
    }

    /**
     * 初始化列表
     */
    private void initList() {
        StudentInfoVo msi = new StudentInfoVo();
        msi.setStusex("男");
        StudentInfoVo fsi = new StudentInfoVo();
        fsi.setStusex("女");
        MsiList = dormMapper.findNotAllocateStudentListByPage(msi);
        FsiList = dormMapper.findNotAllocateStudentListByPage(fsi);

        BuildRoomInfoVo mbi = new BuildRoomInfoVo();
        mbi.setSex("男");
        BuildRoomInfoVo fbi = new BuildRoomInfoVo();
        fbi.setSex("女");
        MbiList = dormMapper.findFreeRoomListByPage(mbi);
        FbiList = dormMapper.findFreeRoomListByPage(fbi);
    }

    /**
     * 分配宿舍（全部分配）
     *
     * @return
     */
    @Override
    public boolean doAssignAll() {
        try {
            clearList();
            initList();
            AllocateRoomToStudent(MbiList, MsiList);//分配女寝
            AllocateRoomToStudent(FbiList, FsiList);//分配男寝
            if (aiList.size() != 0) {
                boolean result = batchInsertAllocationInfo(aiList);// 批量插入宿舍分配信息
                // 插入失败，抛异常
                if (!result) {
                    throw new Exception();
                }
                dormMapper.batchUpdateBuildRoomInfo(updateList);
                updateList.clear();
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * 分配宿舍给学生
     *
     * @param biList 宿舍列表
     * @param siList 学生列表
     */
    public void AllocateRoomToStudent(List<BuildRoomInfo> biList, List<StudentInfo> siList) {
        int index = 0;//第几个学生
        int biLen = biList.size();
        int siLen = siList.size();
        //遍历宿舍,若宿舍或学生已分配完，退出循环
        for (int i = 0; i < biLen && index < siLen; i++) {
            BuildRoomInfo room = biList.get(i);//取一间宿舍
            int free = room.getFree();//获取它的容量
            String brcode = room.getBrcode();
            int j = 1;
            for (; j <= free && index < siLen; j++) {
                StudentInfo si = siList.get(index);
                index++;
                String stuid = si.getStuid();
                AllocationInfo ai = new AllocationInfo();
                ai.setBrcode(brcode);
                ai.setStuid(stuid);
                aiList.add(ai);
                System.out.println(ai);
            }
            //为更新空宿舍表做准备
            updateList.add(new BuildRoomInfo(brcode, free - j + 1));
        }
    }

    /**
     * 显示分配结果
     *
     * @param aiVo
     * @return
     */
    @Override
    public List<AllocationInfo> viewAllocateResult(AllocationInfoVo aiVo) {
//        int page = aiVo.getPage();
//        int row = aiVo.getLimit();
//        int start = (page - 1) * row;
        if (aiList == null || aiList.size() == 0) {
            return null;
        }
        return dormMapper.viewAllocateResult(aiList);
//        return dormMapper.viewAllocateResult(aiList, start, row);
    }

    /**
     * 批量插入宿舍分配信息
     *
     * @param list
     * @return
     */
    public boolean batchInsertAllocationInfo(List<AllocationInfo> list) {
        String classPath = "com.usc.lzh.doms.mapper.DorMMapper.batchInsertAllocationInfo";
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
     * 清空列表
     */
    private void clearList() {
        if (updateList != null) {
            updateList.clear();
        }
        if (MbiList != null) {
            MbiList.clear();
        }
        if (FbiList != null) {
            FbiList.clear();
        }
        if (MsiList != null) {
            MsiList.clear();
        }
        if (FsiList != null) {
            FsiList.clear();
        }
        if (aiList != null) {
            aiList.clear();
        }
    }
}
