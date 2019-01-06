package com.gozi.core.base.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.gozi.core.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class UserToken implements Serializable {
    private static final long serialVersionUID = 8209959970006300745L;
    private String token;//登录token
    private String userId;//用户id
    private String accountName; //用户名
    private String phone; //手机号码
    private Integer status;//用户状态： 0  禁用   1 正常   2 待认证
    private String wxOpenid;//微信用户ID
    private String xOpenid;//微信小程序用户ID
    private Date loginTime;//登录时间
    private Date expiresTime;//在某时失效
    public UserToken() {
        super();
    }
    public UserToken(String token, String userId, String accountName, String phone,Integer status, Integer expiresInSecond) {
        this.token = token;
        this.userId = userId;
        this.accountName = accountName;
        this.phone = phone;
        this.status = status;
        this.loginTime = new Date();
        this.expiresTime = DateUtil.transpositionDate(this.loginTime, Calendar.SECOND, expiresInSecond);
    }
    public void updateExpiresTime(Integer expiresInSecond){
        this.expiresTime = DateUtil.transpositionDate(this.loginTime, Calendar.SECOND, expiresInSecond);
    }
}
