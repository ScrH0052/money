package com.scrh.money.exterface.service;

import com.scrh.money.common.utils.PageModel;
import com.scrh.money.exterface.domain.RechargeRecord;

import java.util.List;

/**
 * 充值业务接口
 *
 * @author ScrH0052
 * @date 2021/8/11
 */
public interface RechargeService {
    /**
     * 创建充值记录
     *
     * @param record 充值记录
     * @return 创建结果
     */
    int createRechargeRecord(RechargeRecord record);

    /**
     * 更新订单状态
     *
     * @param trade_no 订单号
     * @param status   订单状态
     * @return 操作结果
     */
    int updateRechargeByTradeNoAndStatusCode(String trade_no, int status);

    /**
     * 查询页面模型信息
     *
     * @param uid        用户id
     * @param targetPage 目标页
     * @return 页面模型
     */
    PageModel queryPageModelByUidAndTargetPage(Integer uid, Integer targetPage);

    /**
     * 根据用户id和页面模型进行查询充值记录
     *
     * @param uid       用户id
     * @param pageModel 页面模型
     * @return 用户充值记录
     */
    List<RechargeRecord> queryRecordsByUidAndPageModel(Integer uid, PageModel pageModel);
}
