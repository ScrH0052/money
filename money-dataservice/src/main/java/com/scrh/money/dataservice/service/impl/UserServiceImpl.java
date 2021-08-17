package com.scrh.money.dataservice.service.impl;

import com.scrh.money.dataservice.mapper.UserMapper;
import com.scrh.money.exterface.domain.User;
import com.scrh.money.exterface.service.UserService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

/**
 * 用户信息业务实现层
 *
 * @author ScrH0052
 * @date 2021/8/2
 */
@DubboService(interfaceClass = UserService.class, version = "0.0.1", timeout = 20000)
@Component
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private RedisTemplate<Object, Object> redisTemplate;

    private UserMapper userMapper;

    /**
     * 查询总的用户数，由于不需要保持实时更新，故采用5分钟缓存
     *
     * @return 总用户的数量
     */
    @Override
    public Long queryCountAllUsers() {
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        Long countAllUsers = (Long) redisTemplate.opsForValue().get("countAllUsers");
        if (null == countAllUsers) {
            countAllUsers = userMapper.selectCountAllUsers();
            redisTemplate.opsForValue().set("countAllUsers", countAllUsers, Duration.ofMillis(5));
        }

        return userMapper.selectCountAllUsers();
    }

    /**
     * 查询手机号对应用户是否存在
     *
     * @param phone 手机号码
     * @return 查询结果
     */
    @Override
    public boolean queryUserExistByPhone(Long phone) {
        return userMapper.selectCountUserByPhone(phone) > 0;
    }

    /**
     * 保存生成的验证码，时效5分钟
     *
     * @param phone    手机号码
     * @param messCode 数字验证码
     */
    @Override
    public void saveMessCode(String phone, String messCode) {
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(phone + "MessCode", messCode, Duration.ofMinutes(5));
    }

    /**
     * 查询手机号对应验证码
     *
     * @param phone 手机号码
     * @return 查询到的验证码，没有找到时返回null
     */
    @Override
    public String queryMessCode(String phone) {
        return (String) redisTemplate.opsForValue().get(phone + "MessCode");
    }

    /**
     * 将注册第一步的信息暂存储
     *
     * @param someInfo 注册第一步提交信息
     */
    @Override
    public void cacheSomeInfo(Map<String, Object> someInfo) {
        redisTemplate.opsForValue().set(someInfo.get("ppp") + "someInfo", someInfo, Duration.ofMinutes(10));
    }

    /**
     * 校验实名认证中输入的手机号码是否与注册的手机号码一致
     *
     * @param phone 手机号码
     * @return 判断结果
     */
    @Override
    public boolean checkPhone(Long phone) {
        Object o = redisTemplate.opsForValue().get(phone + "someInfo");
        return ObjectUtils.isEmpty(o);
    }

    /**
     * 完成注册，将用户信息插入到数据库中
     *
     * @param user 用户对象
     * @return 插入结果
     */
    @Override
    public boolean registComplete(User user) {
        Map<String, Object> someInfo = (HashMap<String, Object>) redisTemplate.opsForValue().get(user.getPhone() + "someInfo");
        if (ObjectUtils.isEmpty(someInfo)) {
            return false;
        }
        user.setLoginPassword((String) someInfo.get("pMwMd"));
        return userMapper.insert(user) != 0;
    }

    @Override
    public User queryUserByPhoneAndPwd(String loginAct, String md5) {
        //测试方便使用固定帐号避免登录麻烦
//        User user = userMapper.selectUserByPhoneAndPwd(loginAct, md5);
        User user = userMapper.selectUserByPhoneAndPwd("13700000000", "e10adc3949ba59abbe56e057f20f883e");

        return user;
    }

}
