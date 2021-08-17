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

    /**
     * 根据主键来更新产品信息，属性值为null时不予更新
     *
     * @param record 更新的产品信息
     * @return 更新操作结果码
     */
    int updateByPrimaryKeySelective(LoanInfo record);

    /**
     * 根据主键来更新产品信息，属性值为null亦设置值为null
     *
     * @param record 更新的产品信息
     * @return 更新操作结果码
     */
    int updateByPrimaryKey(LoanInfo record);

    /**
     * 查询历史年化收益率
     *
     * @return 历史年化收益率
     */
    Double selectLoanHistoryAvgRate();

    /**
     * 根据parasMap给定的条件进行产品信息的查询
     *
     * @param parasMap 查询信息集合
     * @return 产品信息集合
     */
    List<LoanInfo> selectLoanInfoByParasMap(Map<String, Object> parasMap);

    /**
     * 根据类型号码查询对应类型产品总数
     *
     * @param type 产品类型码
     * @return 产品数量
     */
    Integer selectCountOfLoanProductByTypeCode(Integer type);

    /**
     * 更新产品的剩余可投金额
     *
     * @param parasMap 所需信息集合
     * @return 更新操作结果码
     */
    int updateLoanInfoLeftMoneyByInvest(Map<String, Object> parasMap);
}




