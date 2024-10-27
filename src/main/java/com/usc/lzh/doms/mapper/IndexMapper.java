package com.usc.lzh.doms.mapper;

import com.usc.lzh.doms.entity.Users;
import org.apache.catalina.User;
import org.apache.ibatis.annotations.Param;

public interface IndexMapper {

    /**
     * 查找学生用户
     *
     * @param uid 学号
     * @return
     */
    public Users findStudentUserByUid(String uid);

    /**
     * 查找教师或宿管员用户
     *
     * @param uid 工号
     * @return
     */
    public Users findOtherUserByUid(String uid);

    /**
     * 查找学生用户表里是否存在用户名为uid，密码为upwd的用户
     *
     * @param uid  用户的id
     * @param upwd 密码
     * @return
     */
    public int isExistSUserForUidAndPwd(@Param("uid") String uid, @Param("upwd") String upwd);

    /**
     * 查找管理员用户表里是否存在用户名为uid，密码为upwd的用户
     *
     * @param uid   用户的id
     * @param upwd  密码
     * @param utype 用户类型
     * @return
     */
    public int isExistDUserForUidAndPwd(@Param("uid") String uid, @Param("upwd") String upwd, @Param("utype") Integer utype);

    /**
     * 更新密码
     *
     * @param uid
     * @param newPassword
     * @return
     */
    public int updateSUserPassword(String uid, String newPassword);

    /**
     * 更新密码
     *
     * @param uid
     * @param newPassword
     * @return
     */
    public int updateDUserPassword(String uid, String newPassword);
}
