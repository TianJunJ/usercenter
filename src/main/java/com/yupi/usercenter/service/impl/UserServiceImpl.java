package com.yupi.usercenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yupi.usercenter.service.UserService;
import com.yupi.usercenter.model.domain.User;
import com.yupi.usercenter.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.yupi.usercenter.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author Tangjie
* @description 针对表【user】的数据库操作Service实现
* @createDate 2022-09-01 09:25:51
*/
@Service
@Slf4j  //记录日志的注解
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService {
    @Resource
    private UserMapper userMapper;

    /**
     * 全局定义的盐
     */
    private static final  String SALT="yupi";



    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        //1.校验,使用了apache  import org.apache.commons.lang3.StringUtils
        if(StringUtils.isAnyBlank(userAccount,userPassword,checkPassword))
        {
            // todo 修改为自定义异常
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

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest requst) {

        //1.校验,使用了apache  import org.apache.commons.lang3.StringUtils
        if(StringUtils.isAnyBlank(userAccount,userPassword))
        {
            return null;
        }

        if(userAccount.length()<4){
            return null;
        }
        if(userPassword.length()<8)
        {
            return null;
        }

        //校验账户不能包含特殊字符,利用了正则表达式
        String validPattern = "[\\u00A0\\s\"`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*()——+|{}【】‘；：”“'。，、？]";
        Matcher matcher = Pattern.compile(validPattern).matcher(userAccount);
        if(matcher.find()){
            return null;
        }


        //2.加密
        String encryptPassword = DigestUtils.md5DigestAsHex((SALT+userPassword).getBytes());
        //查询用户是否存在
        /**
         * 此处存在一个可能是bug的地方
         * 如果一个用户的数据的isdelete字段为true，还是否能查找到他的数据？
         * 这个问题由于使用的是mybaitis-plus，，可以修改其配置文件，使得默认查找的是未被删除的用户
         * 在mybaitis-pus中搜索【逻辑删除】即可找到描述
         * 描述如下：
         * 1.在配置文件 application.yml中加入
         * mybatis-plus:
         *   global-config:
         *     db-config:
         *       logic-delete-field: flag # 全局逻辑删除的实体字段名(since 3.3.0,配置后可以忽略不配置步骤2)
         *       logic-delete-value: 1 # 逻辑已删除值(默认为 1)
         *       logic-not-delete-value: 0 # 逻辑未删除值(默认为 0)
         * 2。在model.domain.User中的删除字段加上注解
         * @TableLogic
         * private Integer deleted;
         *
         */
        QueryWrapper<User> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("userAccount",userAccount);
        queryWrapper.eq("userPassword",encryptPassword);
        User user = userMapper.selectOne(queryWrapper);
        //用户不存在
        if(user==null){
            log.info("user login failed, userAccount cannot match userPassword");
            return null;
        }

        //3.用户脱敏
        User safetyUser=getSafetyUser(user);


        //4.记录用户的登录态
        requst.getSession().setAttribute(USER_LOGIN_STATE,safetyUser);

        return safetyUser;


    }

    /**
     * 用户脱敏
     * @param user
     * @return
     */
    @Override
    public User getSafetyUser(User user){
        User safetyUser = new User();
        safetyUser.setId(user.getId());
        safetyUser.setUsername(user.getUsername());
        safetyUser.setUserAccount(user.getUserAccount());
        safetyUser.setAvatarUrl(user.getAvatarUrl());
        safetyUser.setGender(user.getGender());
        safetyUser.setPhone(user.getPhone());
        safetyUser.setEmail(user.getEmail());
        safetyUser.setUserRole(user.getUserRole());
        safetyUser.setUserStatus(user.getUserStatus());
        safetyUser.setCreateTime(user.getCreateTime());
        return safetyUser;
    }
}




