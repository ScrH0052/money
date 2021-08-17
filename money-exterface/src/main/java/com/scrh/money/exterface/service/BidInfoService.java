package com.scrh.money.exterface.service;


import com.scrh.money.exterface.domain.BidInfo;

import java.util.List;
import java.util.Map;

/**
 * 投资记录表服务接口
 *
 * @author ScrH0052
 * @date 2021/8/2
 */
public interface BidInfoService {
    /**
     * 查询总交易金额
     *
     * @return 总交易金额
     */
    Double queryCountAllBidMoney();

    /**
     * 查询
     *
     * @param uid       用户id
     * @param pageStart 开始元素的下标
     * @param pageSize  每页显示条目数
     * @return 交易记录表
     */
    List<BidInfo> queryUserBidInfo(Integer uid, Integer pageStart, Integer pageSize);

    /**
     * 投标，投标失败抛出异常
     *
     * @param parasMap 投标信息集合
     */
    void invest(Map<String, Object> parasMap);
}
