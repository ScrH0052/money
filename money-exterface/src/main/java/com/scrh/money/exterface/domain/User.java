package com.scrh.money.exterface.domain;

import java.io.Serializable;
import java.util.Date;
import java.util.Date;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

/**
* 用户表
* @TableName u_user
*/
@Data
public class User implements Serializable {

    /**
    * 用户ID，主键
    */
    @ApiModelProperty("用户ID，主键")
    private Integer id;
    /**
    * 注册手机号码
    */
    @ApiModelProperty("注册手机号码")
    @Length(max= 11,message="编码长度不能超过11")
    private String phone;
    /**
    * 登录密码，密码长度最大16位
    */
    @ApiModelProperty("登录密码，密码长度最大16位")
    @Length(max= 32,message="编码长度不能超过32")
    private String loginPassword;
    /**
    * 用户姓名
    */
    @ApiModelProperty("用户姓名")
    @Length(max= 16,message="编码长度不能超过16")
    private String name;
    /**
    * 用户身份证号码
    */
    @ApiModelProperty("用户身份证号码")
    @Length(max= 18,message="编码长度不能超过18")
    private String idCard;
    /**
    * 注册时间
    */
    @ApiModelProperty("注册时间")
    private Date addTime;
    /**
    * 最近登录时间
    */
    @ApiModelProperty("最近登录时间")
    private Date lastLoginTime;
    /**
    * 用户头像文件路径
    */
    @ApiModelProperty("用户头像文件路径")
    @Length(max= 50,message="编码长度不能超过50")
    private String headerImage;



}
