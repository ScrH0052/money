package com.scrh.money.dataservice.mapper;

import com.scrh.money.exterface.domain.BidInfo;

import java.util.List;


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
     * 查询总的交易金额
     *
     * @return 总交易金额数
     */
    Double selectCountAllBidMoney();

    /**
     * 通过uid查询用户的投标记录
     *
     * @param uid       用户id
     * @param pageStart 开始元素下标
     * @param pageSize  每页显示条数
     * @return 投标记录表
     */
    List<BidInfo> selectByUid(Integer uid, Integer pageStart, Integer pageSize);
}




