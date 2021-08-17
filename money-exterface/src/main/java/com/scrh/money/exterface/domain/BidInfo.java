package com.scrh.money.exterface.domain;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 投资记录表
 *
 * @author ScrH0052
 * @TableName b_bid_info
 */
@Data
public class BidInfo implements Serializable {

    /**
     * 投标记录ID
     */
    @ApiModelProperty("投标记录ID")
    private Integer id;
    /**
     * 产品ID
     */
    @ApiModelProperty("产品ID")
    private Long loanId;
    @ApiModelProperty("产品名称")
    private String productName;
    /**
     * 用户ID
     */
    @ApiModelProperty("用户ID")
    private Long uid;
    /**
     * 投标金额
     */
    @ApiModelProperty("投标金额")
    private Double bidMoney;
    /**
     * 投标时间
     */
    @ApiModelProperty("投标时间")
    private Date bidTime;
    /**
     * 投标状态
     */
    @ApiModelProperty("投标状态")
    private Integer bidStatus;


    public String getBidTime() {
        return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(bidTime);
    }
}
