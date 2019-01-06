package com.gozi.core.base.dto;

/**
 * 功能：UserToken持有者
 * 备注：
 * @author：grzeng
 * @date：2018/4/25
 */
public class UserTokenHolder {

    private static final ThreadLocal<UserToken> local = new ThreadLocal<>();

    public static UserToken getUserToken() {
        return local.get();
    }

    public static void setUserToken(UserToken userToken) {
        local.set(userToken);
    }

    public static void remove() {
        local.remove();
    }
}
