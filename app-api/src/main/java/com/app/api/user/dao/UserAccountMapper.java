package com.app.api.user.dao;

import com.app.api.user.entity.UserAccount;
import com.app.core.base.dao.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountMapper extends BaseMapper<UserAccount> {
    //@Select("SELECT count(*) as totalRow FROM UserAccount ")
    //Integer getTotalRow();
}