package com.yupi.usercenter.service;
//一个业务接口
import com.yupi.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import java.security.NoSuchAlgorithmException;

/**
* @author Tangjie
* @description 针对表【user】的数据库操作Service
* @createDate 2022-09-01 09:25:51
*/

/*
用户服务
 */

public interface UserService extends IService<User> {

    /**
     * 用户注册
     * @param userAccount 用户账户
     * @param userPassword 用户密码
     * @param checkPassword 校验密码
     * @return 新用户id
     */
    long userRegister(String userAccount,String userPassword,String checkPassword) throws NoSuchAlgorithmException;



}
