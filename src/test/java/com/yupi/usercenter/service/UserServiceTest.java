package com.yupi.usercenter.service;
import java.util.Date;

import com.yupi.usercenter.model.domain.User;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;


import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;
/*
用户服务测试
 */
@SpringBootTest
class UserServiceTest {

    @Resource
    private  UserService userService;

    @Test
    public void testAddUser()
    {
        User user = new User();
        //下面为自动生成的
        //user.setId(0L);
        user.setUsername("dogyupi");
        user.setUserAccount("123");
        user.setAvatarUrl("https://img-home.csdnimg.cn/images/20201124032511.png");
        user.setGender(0);
        user.setUserPassword("xxx");
        user.setPhone("123");
        user.setEmail("456");
        user.setUserStatus(0);


        boolean result = userService.save(user);
        //验证框架是否会自动添加ID
        System.out.println(user.getId());
        //添加断言
        assertTrue(result);
    }
}