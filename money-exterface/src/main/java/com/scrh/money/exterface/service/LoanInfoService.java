package com.scrh.money.exterface.service;

import com.scrh.money.exterface.domain.LoanInfo;

import java.util.List;
import java.util.Map;

/**
 * 产品信息服务接口
 * @author ScrH0052
 * @date 2021/8/2
 */
public interface LoanInfoService {
    /**
     * 查询历史年化收益率
     * @return 历史年化收益率
     */
    Double queryLoanInfoAvgRate();

    /**
     * 通过parasMap查询产品信息集合
     * @param parasMap 查询条件：type-种类，start-开始索引，content-查找条数
     * @return 符合查询条件的产品列表
     */
    List<LoanInfo> queryLoanInfoByParasMap(Map<String, Object> parasMap);

    /**
     * 根据目标页码以及产品类型码进行查询获取产品信息列表
     * @param targetPage 目标页码
     * @param type 产品类型编码
     * @return 对应产品信息列表
     */
    Map<String,Object> queryLoanProductByTypeCode(Integer targetPage, Integer type);

    /**
     * 根据产品ID查询获取对应产品信息
     * @param id 查询的产品ID
     * @return id对应产品的信息
     */
    LoanInfo queryLoanInfoById(Long id);
}
