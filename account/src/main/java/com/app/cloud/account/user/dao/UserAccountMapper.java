package com.app.cloud.account.user.dao;

import com.app.cloud.account.user.entity.UserAccount;
import com.app.cloud.web.BaseMapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

//@Repository
public interface UserAccountMapper extends BaseMapper<UserAccount> {
    @Select("SELECT count(*) as totalRow FROM UserAccount ")
    Integer getTotalRow();
}