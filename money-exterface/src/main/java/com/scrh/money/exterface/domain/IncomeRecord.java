package com.scrh.money.exterface.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 收益记录表
 *
 * @author ScrH0052
 */
@Data
public class IncomeRecord implements Serializable {

    /**
     * 收益记录ID
     */
    @ApiModelProperty("")
    private Integer id;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Integer uid;
    /**
     * 产品ID
     */
    @ApiModelProperty("产品ID")
    private Integer loanId;
    /**
     * 投标记录ID
     */
    @ApiModelProperty("投标记录ID")
    private Integer bidId;
    /**
     * 投资金额
     */
    @ApiModelProperty("投资金额")
    private Double bidMoney;
    /**
     * 收益时间
     */
    @ApiModelProperty("收益时间")
    private Date incomeDate;
    /**
     * 收益金额
     */
    @ApiModelProperty("收益金额")
    private Double incomeMoney;
    /**
     * 收益状态（0未返，1已返）
     */
    @ApiModelProperty("收益状态（0未返，1已返）")
    private Integer incomeStatus;

    public String getIncomeDate() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(incomeDate);
    }
}
