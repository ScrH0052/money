package com.scrh.money.exterface.domain;

import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 充值记录表
* @TableName b_recharge_record
*/
@Data
public class RechargeRecord implements Serializable {

    /**
    * 充值记录ID
    */
    @ApiModelProperty("")
    private Integer id;
    /**
    * 用户id
    */
    @ApiModelProperty("用户id")
    private Integer uid;
    /**
    * 充值订单号
    */
    @ApiModelProperty("充值订单号")
    @Length(max= 50,message="编码长度不能超过50")
    private String rechargeNo;
    /**
    * 充值订单状态（0充值中，1充值成功，2充值失败）
    */
    @ApiModelProperty("充值订单状态（0充值中，1充值成功，2充值失败）")
    @Length(max= 50,message="编码长度不能超过50")
    private String rechargeStatus;
    /**
    * 充值金额
    */
    @ApiModelProperty("充值金额")
    private Double rechargeMoney;
    /**
    * 充值时间
    */
    @ApiModelProperty("充值时间")
    private Date rechargeTime;
    /**
    * 充值描述
    */
    @ApiModelProperty("充值描述")
    @Length(max= 50,message="编码长度不能超过50")
    private String rechargeDesc;


}
