package com.scrh.money.dataservice.mapper;

import com.scrh.money.exterface.domain.User;

/**
 * 用户数据库查询接口
 * @author ScrH0052
 */
public interface UserMapper {
    /**
     * 通过ID删除数据库中对应用户
     * @param id 用户ID
     * @return 删除用户操作结果，1为成功，0为失败
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入新的用户数据
     * @param record 用户实例对象
     * @return 操作结果，1为成功，0为失败
     */
    int insert(User record);

    /**
     * 通过主键ID查询用户信息
     * @param id 用户ID
     * @return 查询到的对应用户信息封装到对象中，如果查询失败则对象为null
     */
    User selectByPrimaryKey(Long id);

    /**
     * 根据给定的用户对象中的主键更新对应用户信息，对象中对应属性为null时，则不对对应属性进行修改
     * @param record 用户实例对象
     * @return 操作结果，1为成功，0为失败
     */
    int updateByPrimaryKeySelective(User record);

    /**
     * 根据给定的用户对象中的主键更新对应用户信息
     * @param record 用户实例对象
     * @return 操作结果，1为成功，0为失败
     */
    int updateByPrimaryKey(User record);

    /**
     * 查询总用户数量
     * @return 用户总数
     */
    Long selectCountAllUsers();

    /**
     * 校验手机号是否存在对应用户
     * @param phone 手机号码
     * @return 操作结果，1为成功，0为失败
     */
    int selectCountUserByPhone(Long phone);
}




