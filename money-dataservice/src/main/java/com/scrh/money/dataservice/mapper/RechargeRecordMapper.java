package com.scrh.money.dataservice.mapper;

import com.scrh.money.exterface.domain.RechargeRecord;

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

}




