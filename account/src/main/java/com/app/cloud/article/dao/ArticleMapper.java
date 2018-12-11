package com.app.cloud.article.dao;

import com.app.cloud.article.entity.Article;
import com.app.cloud.user.account.entity.UserAccount;
import com.app.core.base.dao.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    //@Select("SELECT count(*) as totalRow FROM UserAccount ")
    //Integer getTotalRow();
}