package com.app.api.article.service.impl;

import com.app.api.article.dao.ArticleMapper;
import com.app.api.article.entity.Article;
import com.app.api.article.service.ArticleService;
import com.app.core.base.service.BaseServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ArticleServiceImpl extends BaseServiceImpl<ArticleMapper, Article> implements ArticleService {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Scheduled(cron = "0 0/5 * * * ?")//每五分钟
    //@Scheduled(cron = "0 0 2 * * ?")//每天半夜两点
    @Transactional(rollbackFor = Exception.class)
    public void timedTaskCancelOrder() {
        logger.info("====================定时将已过期月嫂证件设置为已过期 开始========================");

        logger.info("====================定时将已过期月嫂证件设置为已过期 结束========================");
    }

}