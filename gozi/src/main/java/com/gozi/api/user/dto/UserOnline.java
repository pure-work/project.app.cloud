package com.gozi.api.user.dto;

import com.gozi.core.base.util.DateUtil;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@Getter
@Setter
public class UserOnline implements Serializable {
    private static final long serialVersionUID = 8209959970006300745L;
    private String userId;//用户id
    private String token;//登录token
    private Date expiresTime;//在某时失效
    public UserOnline() {
        super();
    }
    public UserOnline(String token, String userId, Integer expiresInSecond) {
        this.token = token;
        this.userId = userId;
    }

}
