package com.scrh.money.dataservice.service.impl;

import com.scrh.money.common.exception.BidException;
import com.scrh.money.common.exception.ExceptionEnum;
import com.scrh.money.dataservice.mapper.BidInfoMapper;
import com.scrh.money.dataservice.mapper.FinanceAccountMapper;
import com.scrh.money.dataservice.mapper.LoanInfoMapper;
import com.scrh.money.exterface.domain.BidInfo;
import com.scrh.money.exterface.domain.LoanInfo;
import com.scrh.money.exterface.service.BidInfoService;
import lombok.AllArgsConstructor;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 投资记录表业务层
 *
 * @author ScrH0052
 * @date 2021/8/2
 */
@DubboService(interfaceClass = BidInfoService.class, version = "0.0.1", timeout = 20000)
@Component
@AllArgsConstructor
public class BidInfoServiceImpl implements BidInfoService {

    private RedisTemplate<Object, Object> redisTemplate;

    private BidInfoMapper bidInfoMapper;

    private LoanInfoMapper loanInfoMapper;

    private FinanceAccountMapper financeAccountMapper;

    @Override
    public Double queryCountAllBidMoney() {
        Double countAllBidMoney = (Double) redisTemplate.opsForValue().get("countAllBidMoney");
        if (null == countAllBidMoney) {
            countAllBidMoney = bidInfoMapper.selectCountAllBidMoney();
            redisTemplate.opsForValue().set("countAllBidMoney", countAllBidMoney, Duration.ofMinutes(5));
        }
        return countAllBidMoney;
    }

    @Override
    public List<BidInfo> queryUserBidInfo(Integer uid, Integer pageStart, Integer pageSize) {
        return bidInfoMapper.selectByUid(uid, pageStart, pageSize);
    }

    /**
     * 投标，投标失败抛出异常
     *
     * @param parasMap 投标信息集合
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public void invest(Map<String, Object> parasMap) {

        Double bidMoney = (Double) parasMap.get("bidMoney");

        //账户余额不足时回滚抛出异常
        if (financeAccountMapper.updateByUidAndBidMoney(parasMap) == 0) {
            throw new BidException(ExceptionEnum.BID_ERROR_ACCOUNT_MONEY_NOT_ENOUGH);
        }


        //判断当前库存余额是否足够
        LoanInfo loan = loanInfoMapper.selectByPrimaryKey((Long) parasMap.get("loanId"));
        if (loan.getLeftProductMoney() < bidMoney) {
            throw new BidException(ExceptionEnum.BID_LOAN_LEFT_NOT_ENOUGH);
        }
        parasMap.put("version", loan.getVersion());
        if (loanInfoMapper.updateLoanInfoLeftMoneyByInvest(parasMap) == 0) {
            throw new BidException(ExceptionEnum.BID_LOAN_ERROR_LEFT_MONEY_UPDATE);
        }
        loan = loanInfoMapper.selectByPrimaryKey((Long) parasMap.get("loanId"));
        if (loan.getLeftProductMoney() == 0 && loan.getProductStatus() == 0) {
            loan.setProductStatus(1);
            loan.setProductFullTime(new Date());
            if (loanInfoMapper.updateByPrimaryKeySelective(loan) == 0) {
                throw new BidException(ExceptionEnum.BID_LOAN_LEFT_NOT_ENOUGH);
            }
        }

        //生成投资记录
        BidInfo bidInfo = new BidInfo();
        bidInfo.setBidMoney((Double) parasMap.get("bidMoney"));
        bidInfo.setBidStatus(1);
        bidInfo.setBidTime(new Date());
        bidInfo.setLoanId((Long) parasMap.get("loanId"));
        bidInfo.setUid((Long) parasMap.get("uid"));
        if (bidInfoMapper.insert(bidInfo) == 0) {
            throw new BidException(ExceptionEnum.BID_ERROR_CREATE_RECORD);
        }
    }
}
