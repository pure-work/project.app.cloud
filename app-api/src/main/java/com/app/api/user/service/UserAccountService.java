package com.app.api.user.service;


import com.app.api.user.dao.UserAccountMapper;
import com.app.api.user.entity.UserAccount;
import com.app.core.base.service.BaseService;

public interface UserAccountService extends BaseService<UserAccountMapper, UserAccount> {
    Integer getTotalRow();
}