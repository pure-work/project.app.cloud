package com.app.cloud.article.service;


import com.app.cloud.article.dao.ArticleMapper;
import com.app.cloud.article.entity.Article;
import com.app.cloud.user.account.dao.UserAccountMapper;
import com.app.cloud.user.account.entity.UserAccount;
import com.app.core.base.service.BaseService;

public interface ArticleService extends BaseService<ArticleMapper, Article> {

}