package com.yupi.usercenter.model.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName user
 * 此处的内容一定要与数据库中的内容一致，一一对应
 * 最终从数据库查询的数值，会被赋值到该对象中
 * 使用Mybatisx插件自动生成
 */
@TableName(value ="user")
@Data
public class User implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Long id;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String userAccount;

    /**
     * 用户头像
     */
    private String avatarUrl;

    /**
     * 
     */
    private Integer gender;

    /**
     * 
     */
    private String userPassword;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private String email;

    /**
     * 0-正常
     */
    private Integer userStatus;




    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * @TableLogic 为 mybaitis-plus的逻辑删除注解
     */
    @TableLogic
    private Integer isDelete;

    /**
     * 用户角色 0--普通用户，1--管理员
     */
    private Integer userRole;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;
}