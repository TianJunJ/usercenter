package com.yupi.usercenter.service;
import java.security.NoSuchAlgorithmException;

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

    @Test
    void userRegister() throws NoSuchAlgorithmException {
        //完整的单元测试

        String userAccount="yupi";
        String userPassword="";
        String checkPassword="123456";

        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount="yu";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount="yupi";
        userPassword="123456";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount="yu pi";
        userPassword="12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        checkPassword="123456789";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);
        userAccount="dogyupi";
        checkPassword="12345678";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);

        userAccount="yupi";
        result = userService.userRegister(userAccount, userPassword, checkPassword);
        Assertions.assertEquals(-1,result);



    }
}