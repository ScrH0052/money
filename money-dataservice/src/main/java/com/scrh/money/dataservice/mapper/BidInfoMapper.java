package com.scrh.money.dataservice.mapper;

import com.scrh.money.exterface.domain.BidInfo;
import org.apache.ibatis.annotations.Mapper;


/**
 * @Entity com.scrh.money.common.pojo.BidInfo
 */
public interface BidInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(BidInfo record);

    int insertSelective(BidInfo record);

    BidInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(BidInfo record);

    int updateByPrimaryKey(BidInfo record);

    /**
     * @return 总交易金额数
     */
    Double selectCountAllBidMoney();
}




