package com.usc.lzh.doms.service;

import com.usc.lzh.doms.entity.Users;

public interface IndexService {

    /**
     * 查询用户信息，判断是否存在该用户
     *
     * @param uid   用户id
     * @param upwd  用户名
     * @param utype 用户类型
     * @return
     */
    public Users findUserByuId(String uid, String upwd, Integer utype);

    /**
     * 修改用户密码
     *
     * @param uid         用户的id
     * @param utype       用户的类型
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return
     */
    public int updatePassword(String uid, Integer utype, String oldPassword, String newPassword);
}
