package com.scrh.money.exterface.service;

import com.scrh.money.exterface.domain.User;

import java.util.Map;

/**
 * 用户业务功能服务层
 * @author ScrH0052
 * @date 2021/8/2
 */
public interface UserService {
    /**
     * 统计总用户数量
     * @return 总的用户数量
     */
    Long queryCountAllUsers();

    /**
     * 查询手机号对应用户是否存在
     * @param phone 手机号码
     * @return 查询结果
     */
    boolean queryUserExistByPhone(Long phone);

    /**
     * 保存生成的验证码
     * @param phone 手机号码
     * @param messCode 数字验证码
     */
    void saveMessCode(String phone, String messCode);

    /**
     * 查询手机号对应验证码
     * @param phone 手机号码
     * @return 查询到的验证码，没有找到时返回null
     */
    String queryMessCode(String phone);

    /**
     * 将注册第一步的信息暂存储
     * @param someInfo 注册第一步提交信息
     */
    void cacheSomeInfo(Map<String, Object> someInfo);

    /**
     * 校验实名认证中输入的手机号码是否与注册的手机号码一致
     * @param phone 手机号码
     * @return 判断结果
     */
    boolean checkPhone(Long phone);

    /**
     * 完成注册，将用户信息插入到数据库中
     * @param user 用户对象
     * @return 插入结果
     */
    boolean registComplete(User user);
}
