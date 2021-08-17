package com.scrh.money.dataservice.mapper;

import com.scrh.money.exterface.domain.RechargeRecord;

import java.util.List;

/**
 * @Entity com.scrh.money.common.pojo.RechargeRecord
 */
public interface RechargeRecordMapper {

    int deleteByPrimaryKey(Long id);

    int insert(RechargeRecord record);

    int insertSelective(RechargeRecord record);

    RechargeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(RechargeRecord record);

    int updateByPrimaryKey(RechargeRecord record);

    /**
     * 根据订单号和给定的状态码更新充值订单状态
     *
     * @param trade_no 订单号
     * @param status   状态码
     * @return 更新操作结果
     */
    int updateRechargeByTradeNoAndStatusCode(String trade_no, int status);

    /**
     * 统计用户充值记录数量
     *
     * @param uid 用户id
     * @return 用户充值记录数
     */
    Integer selectCountByUid(Integer uid);

    /**
     * 查询用户充值记录
     *
     * @param uid        用户id
     * @param start      开始元素下标
     * @param targetPage 目标页
     * @return 用户充值记录
     */
    List<RechargeRecord> queryRecordsByUidAndPageModel(Integer uid, Integer start, Integer pageSize);
}




