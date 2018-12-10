package com.app.api.article.dao;

import com.app.api.article.entity.Article;
import com.app.core.base.dao.BaseMapper;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleMapper extends BaseMapper<Article> {
    //@Select("SELECT count(*) as totalRow FROM UserAccount ")
    //Integer getTotalRow();
}