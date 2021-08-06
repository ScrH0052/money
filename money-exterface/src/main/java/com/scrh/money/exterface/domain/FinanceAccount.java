package com.scrh.money.exterface.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
* 用户财务资金账户表
* @TableName u_finance_account
*/
@Data
public class FinanceAccount implements Serializable {

    /**
    * 财务资金账户ID
    */
    @ApiModelProperty("")
    private Integer id;
    /**
    * 用户ID
    */
    @ApiModelProperty("用户ID")
    private Integer uid;
    /**
    * 用户可用资金
    */
    @ApiModelProperty("用户可用资金")
    private Double availableMoney;




}
