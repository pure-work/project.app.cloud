package com.app.cloud.user.account.service.impl;

import com.app.cloud.user.account.dao.UserAccountMapper;
import com.app.cloud.user.account.entity.UserAccount;
import com.app.cloud.user.account.service.UserAccountService;
import com.app.core.base.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserAccountServiceImpl extends BaseServiceImpl<UserAccountMapper, UserAccount> implements UserAccountService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    public Integer getTotalRow(){
        return baseMapper.selectCount(new UserAccount());
        //return userAccountMapper.getTotalRow();
    }

    @Scheduled(cron = "0 0/5 * * * ?")//每五分钟
    //@Scheduled(cron = "0 0 2 * * ?")//每天半夜两点
    @Transactional(rollbackFor = Exception.class)
    public void timedTaskCancelOrder() {
        logger.info("====================定时将已过期月嫂证件设置为已过期 开始========================");

        logger.info("====================定时将已过期月嫂证件设置为已过期 结束========================");
    }

}