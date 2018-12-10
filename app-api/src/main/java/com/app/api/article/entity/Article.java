package com.app.api.article.entity;

import com.app.core.base.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Table;

@Table(name = "article")
@ApiModel(value = "文章")
@Getter
@Setter
public class Article extends BaseEntity {
    private static final long serialVersionUID = 934278149340893196L;

    @ApiModelProperty(value="标题", required = true)
    @Column(name = "title")
    private String title;
    @ApiModelProperty(value="简介", required = true)
    @Column(name = "introduce")
    private String introduce;
    @ApiModelProperty(value="标题图地址", required = true)
    @Column(name = "head_image_url")
    private String headImageUrl;
    @ApiModelProperty(value="文章地址", required = true)
    @Column(name = "url")
    private String url;

}
