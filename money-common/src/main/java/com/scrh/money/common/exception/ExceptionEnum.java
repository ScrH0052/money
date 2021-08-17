package com.scrh.money.common.exception;

/**
 * 异常信息枚举
 *
 * @author ScrH0052
 * @date 2021/8/11
 */
public enum ExceptionEnum {
    //投资过程中的异常
    BID_LOAN_LEFT_NOT_ENOUGH("产品已满标！"),
    BID_ERROR_ACCOUNT_MONEY_NOT_ENOUGH("账户余额不足，请充值！"),
    BID_LOAN_ERROR_LEFT_MONEY_UPDATE("产品可投余额更新失败！"),
    BID_ERROR_CREATE_RECORD("投资记录创建失败！");


    private String message;

    ExceptionEnum(String message) {

        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
