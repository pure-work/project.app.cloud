package com.app.cloud.account.user.service.impl;

import com.app.cloud.account.user.dao.UserAccountMapper;
import com.app.cloud.account.user.service.UserAccountService;
import com.netflix.discovery.converters.Auto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.common.BaseMapper;

//@Service
public class UserAccountServiceImpl implements UserAccountService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private UserAccountMapper userAccountMapper;

    public Integer getTotalRow(){
        return userAccountMapper.getTotalRow();
    }

    @Scheduled(cron = "0 0/5 * * * ?")//每五分钟
    //@Scheduled(cron = "0 0 2 * * ?")//每天半夜两点
    @Transactional(rollbackFor = Exception.class)
    public void timedTaskCancelOrder() {
        logger.info("====================定时将已过期月嫂证件设置为已过期 开始========================");

        logger.info("====================定时将已过期月嫂证件设置为已过期 结束========================");
    }

}