package com.app.cloud.article.controller;

import com.app.cloud.user.account.entity.UserAccount;
import com.app.cloud.user.account.service.UserAccountService;
import com.app.core.base.dto.ResultVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequestMapping("/article")
@Api(value="Article", description = "文章模块")
public class ArticleController {

    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private UserAccountService userAccountService;

    /**
     * 获取证书类型
     * @param request
     * @return
     */
    @RequestMapping("/all")
    @ApiOperation(value="获取所有用户列表" , httpMethod="GET", notes="通用接口")
    @ApiImplicitParams({
            //@ApiImplicitParam(name = "platform", value = "平台(1.APP 2.微信)", required = true, paramType = "header", dataType = "String"),
            //@ApiImplicitParam(name = "version", value = "版本", required = true, paramType = "header", dataType = "String"),
            //@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header", dataType = "String"),
            //@ApiImplicitParam(name = "cityId", value = "城市ID", required = false, paramType = "query", dataType = "String")
    })
    public ResultVo<List<UserAccount>> getAll(HttpServletRequest request) {
        List<UserAccount> userAccountList = userAccountService.findAll();
        return ResultVo.success(userAccountList);
    }
}
