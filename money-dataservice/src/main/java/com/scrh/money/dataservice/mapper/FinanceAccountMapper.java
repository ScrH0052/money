package com.scrh.money.dataservice.mapper;

import com.scrh.money.exterface.domain.FinanceAccount;

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

}




