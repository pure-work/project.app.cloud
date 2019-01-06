package com.gozi.api.user.enums;

import lombok.Getter;

@Getter
public enum UserStatus {
    NORMAL(1, "正常"),
    LOCKED(2, "锁定");

    private Integer code;
    private String desc;

    UserStatus(Integer code, String desc){
        this.code = code;
        this.desc = desc;
    }
}
