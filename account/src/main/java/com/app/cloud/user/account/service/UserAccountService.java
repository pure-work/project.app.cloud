package com.app.cloud.user.account.service;


import com.app.cloud.user.account.dao.UserAccountMapper;
import com.app.cloud.user.account.entity.UserAccount;
import com.app.core.base.service.BaseService;

public interface UserAccountService extends BaseService<UserAccountMapper, UserAccount> {
    Integer getTotalRow();
}