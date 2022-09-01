package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.service.UserService;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
* @author Tangjie
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-09-01 09:25:51
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    @Resource
    private UserMapper userMapper;
    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) throws NoSuchAlgorithmException {
        //1.校验,使用了apache  import org.apache.commons.lang3.StringUtils
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword))
        {
            return -1;
        }

        if(userAccount.length()<4){
            return -1;
        }
        if(checkPassword.length()<8||checkPassword.length()<8)
        {
            return -1;
        }

        //校验账户不能包含特殊字符,利用了正则表达式
        String validPattern = "[\\u00A0\\s\"`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*()——+|{}【】‘；：”“'。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            return -1;
        }

        //密码和校验密码相同
        //此处，比较字符串只能用equal
        if(!userPassword.equals(checkPassword)){
            return -1;
        }


        //账户不能重复
        //该操作查询了数据库，放在基本的判断后可以达到效率最大化
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        //使用mapper操作数据库
        long count = userMapper.selectCount(queryWrapper);
        if(count>0)
        {
            return -1;
        }

        //2.加密
        final String SALT="yupi";
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        //3.插入数据
        User user =new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        //由于id为long类型此处有可能保存失败
        boolean saveResult = this.save(user);
        if(!saveResult){
            return -1;
        }

        return user.getId();

    }
}




