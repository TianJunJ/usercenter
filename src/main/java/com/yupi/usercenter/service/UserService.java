package com.yupi.usercenter.service;
//一个业务接口
import com.yupi.usercenter.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;

import javax.servlet.http.HttpServletRequest;

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
    long userRegister(String userAccount,String userPassword,String checkPassword);

    /**
     * 用户登录
     *
     * @param userAccount  用户账户
     * @param userPassword 用户密码
     * @param requst
     * @return t脱敏后的用户信息
     */
    User userLogin(String userAccount, String userPassword , HttpServletRequest requst);

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    User getSafetyUser(User user);
}
