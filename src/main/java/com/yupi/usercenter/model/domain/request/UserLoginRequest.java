package com.yupi.usercenter.model.domain.request;

import lombok.Data;

import java.io.Serializable;

/**
 * 用户登录请求体
 */
@Data //生成get与set方法的注解 （lombox）
public class UserLoginRequest implements Serializable {
    //下载了一个可以生成序列化号的插件
    private static final long serialVersionUID = 3191241716373120793L;

    private String userAccount;
    private String userPassword;
}
