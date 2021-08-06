package com.scrh.money.exterface.service;


/**
 * 投资记录表服务接口
 * @author ScrH0052
 * @date 2021/8/2
 */
public interface BidInfoService {
    /**
     * 查询总交易金额
     * @return 总交易金额
     */
    Double queryCountAllBidMoney();
}
