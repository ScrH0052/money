package com.scrh.money.dataservice.service.impl;

import com.scrh.money.common.utils.PageModel;
import com.scrh.money.dataservice.mapper.RechargeRecordMapper;
import com.scrh.money.exterface.domain.RechargeRecord;
import com.scrh.money.exterface.service.RechargeService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 充值业务实现类
 *
 * @author ScrH0052
 * @date 2021/8/11
 */
@DubboService(interfaceClass = RechargeService.class, version = "0.0.1", timeout = 20000)
@Component
@AllArgsConstructor
public class RechargeServiceImpl implements RechargeService {

    private RechargeRecordMapper rechargeRecordMapper;

    @Override
    public int createRechargeRecord(RechargeRecord record) {
        return rechargeRecordMapper.insertSelective(record);
    }

    @Override
    public int updateRechargeByTradeNoAndStatusCode(String trade_no, int status) {
        return rechargeRecordMapper.updateRechargeByTradeNoAndStatusCode(trade_no, status);
    }

    @Override
    public PageModel queryPageModelByUidAndTargetPage(Integer uid, Integer targetPage) {
        PageModel pageModel = new PageModel();
        pageModel.setTargetPage(targetPage);
        pageModel.setTotalCount(rechargeRecordMapper.selectCountByUid(uid));
        pageModel.setPageSize(6);
        return pageModel;
    }

    @Override
    public List<RechargeRecord> queryRecordsByUidAndPageModel(Integer uid, PageModel pageModel) {
        return rechargeRecordMapper.queryRecordsByUidAndPageModel(uid, pageModel.getStart(), pageModel.getPageSize());
    }
}
