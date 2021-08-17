package com.scrh.money.exterface.service;

import com.scrh.money.exterface.domain.FinanceAccount;

/**
 * 账户余额业务接口
 *
 * @author ScrH0052
 * @date 2021/8/9
 */
public interface FinanceAccountService {
    /**
     * 根据用户id查询账户余额
     *
     * @param id 用户id
     * @return 用户账户对象
     */
    FinanceAccount queryAccountMoney(Integer id);
}
