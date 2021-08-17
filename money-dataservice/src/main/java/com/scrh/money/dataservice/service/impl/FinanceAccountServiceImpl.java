package com.scrh.money.dataservice.service.impl;

import com.scrh.money.dataservice.mapper.FinanceAccountMapper;
import com.scrh.money.exterface.domain.FinanceAccount;
import com.scrh.money.exterface.service.FinanceAccountService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.stereotype.Component;

/**
 * @author ScrH0052
 * @date 2021/8/9
 */
@DubboService(interfaceClass = FinanceAccountService.class, version = "0.0.1", timeout = 20000)
@Component
@AllArgsConstructor
public class FinanceAccountServiceImpl implements FinanceAccountService {
    private FinanceAccountMapper financeAccountMapper;

    @Override
    public FinanceAccount queryAccountMoney(Integer id) {
        FinanceAccount financeAccount = financeAccountMapper.selectByUid(id);
        if (ObjectUtils.isEmpty(financeAccount)) {
            financeAccount = new FinanceAccount();
            financeAccount.setUid(id);
            financeAccount.setAvailableMoney(0.00);
            financeAccountMapper.insert(financeAccount);
        }
        return financeAccount;
    }
}
