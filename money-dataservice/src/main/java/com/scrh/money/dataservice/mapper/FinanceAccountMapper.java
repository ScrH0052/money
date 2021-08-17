package com.scrh.money.dataservice.mapper;

import com.scrh.money.exterface.domain.FinanceAccount;

import java.util.Map;

/**
 * @Entity com.scrh.money.common.pojo.FinanceAccount
 */
public interface FinanceAccountMapper {

    int deleteByPrimaryKey(Long id);

    int insert(FinanceAccount record);

    int insertSelective(FinanceAccount record);

    FinanceAccount selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(FinanceAccount record);

    int updateByPrimaryKey(FinanceAccount record);

    /**
     * 根据用户id查询账户信息
     *
     * @param uid 用户id
     * @return 用户的账户对象
     */
    FinanceAccount selectByUid(Integer uid);

    /**
     * 根据用户id和投标金额对对应账户进行扣款
     *
     * @param parasMap 投资信息
     * @return 操作结果代码
     */
    int updateByUidAndBidMoney(Map<String, Object> parasMap);
}




