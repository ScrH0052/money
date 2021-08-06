package com.scrh.money.dataservice.mapper;

import com.scrh.money.exterface.domain.LoanInfo;

import java.util.List;
import java.util.Map;

/**
 * @Entity com.scrh.money.common.pojo.LoanInfo
 */
public interface LoanInfoMapper {

    int deleteByPrimaryKey(Long id);

    int insert(LoanInfo record);

    int insertSelective(LoanInfo record);

    LoanInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(LoanInfo record);

    int updateByPrimaryKey(LoanInfo record);
    //查询历史年化收益率
    Double selectLoanHistoryAvgRate();
    //根据parasMap给定的条件进行产品信息的查询
    List<LoanInfo> selectLoanInfoByParasMap(Map<String, Object> parasMap);
    //根据类型号码查询对应类型产品总数
    Integer selectCountOfLoanProductByTypeCode(Integer type);
}




