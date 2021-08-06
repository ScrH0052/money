package com.scrh.money.dataservice.mapper;

import com.scrh.money.exterface.domain.IncomeRecord;

/**
 * @Entity com.scrh.money.common.pojo.IncomeRecord
 */
public interface IncomeRecordMapper {

    int deleteByPrimaryKey(Long id);

    int insert(IncomeRecord record);

    int insertSelective(IncomeRecord record);

    IncomeRecord selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(IncomeRecord record);

    int updateByPrimaryKey(IncomeRecord record);

}




