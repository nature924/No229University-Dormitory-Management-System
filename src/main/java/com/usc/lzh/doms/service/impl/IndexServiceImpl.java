package com.usc.lzh.doms.service.impl;

import com.usc.lzh.doms.entity.Users;
import com.usc.lzh.doms.mapper.IndexMapper;
import com.usc.lzh.doms.service.IndexService;

import org.apache.catalina.User;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class IndexServiceImpl implements IndexService {

    @Resource
    private IndexMapper indexMapper;

    /**
     * 查询用户信息，判断是否存在该用户
     *
     * @param uid   用户id
     * @param upwd  用户名
     * @param utype 用户类型
     * @return
     */
    @Override
    public Users findUserByuId(String uid, String upwd, Integer utype) {
        // 根据用户类型去到对应的表里查找用户
        Users user = null;
        if (utype == 0) {
            user = indexMapper.findStudentUserByUid(uid);
        } else {
            user = indexMapper.findOtherUserByUid(uid);
        }
        System.out.println(user);
        // 如果存在该用户，判断密码对不对
        if (user != null) {
            String pwd = user.getUpwd();
            Integer type = user.getUtype();
            if (upwd.equals(pwd) && utype == type) {
                return user;
            }
        }
        return null;
    }

    /**
     * 修改用户密码
     *
     * @param uid         用户的id
     * @param utype       用户的类型
     * @param oldPassword 旧密码
     * @param newPassword 新密码
     * @return 0表示不存在用户，无法更新；1表示更新完成；-1表示更新失败
     */
    @Override
    public int updatePassword(String uid, Integer utype, String oldPassword, String newPassword) {
        /*
        先找对应表里存不存在uid和uname的用户
        如果存在，再更新密码
         */
        try {
            // 学生用户
            if (utype == 0) {
                int isExist = indexMapper.isExistSUserForUidAndPwd(uid, oldPassword);
                if (isExist == 1) {
                    int result = indexMapper.updateSUserPassword(uid, newPassword);
                    return result;
                } else {
                    return 0;
                }
            } else {
                // 管理员(宿管/教师)用户
                int isExist = indexMapper.isExistDUserForUidAndPwd(uid, oldPassword, utype);
                if (isExist == 1) {
                    int result = indexMapper.updateDUserPassword(uid, newPassword);
                    return result;
                } else {
                    return 0;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        }
    }
}
