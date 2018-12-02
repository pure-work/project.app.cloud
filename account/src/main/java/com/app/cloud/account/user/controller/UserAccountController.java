package com.app.cloud.account.user.controller;

import com.app.cloud.account.user.entity.UserAccount;
import com.app.cloud.account.user.service.UserAccountService;
import com.netflix.discovery.converters.Auto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

//@RestController
//@RequestMapping("/user")
@Api(value="UserAccount", description = "用户账户模块")
public class UserAccountController {

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
            @ApiImplicitParam(name = "platform", value = "平台(1.APP 2.微信)", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "version", value = "版本", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header", dataType = "String"),
            //@ApiImplicitParam(name = "cityId", value = "城市ID", required = false, paramType = "query", dataType = "String")
    })
    //public ResultVo<List<UserAccount>> getAll(HttpServletRequest request) {
    public String getAll(HttpServletRequest request) {
        /*
        UserToken userToken = getUserToken( request);
        Integer userType = userToken.getUserType();
        Example example = new Example(CertificateType.class);
        String condition = "status = 1 and (user_type = 0  or user_type = "+userType+")";
        example.createCriteria().andCondition(condition);
        example.setOrderByClause("orders ASC");
        List<CertificateType> certificateTypeList = certificateTypeService.findByExample(example);
        return ResultVo.success(certificateTypeList);
        */
        //List<UserAccount> userAccountList = userAccountService.findAll();

        //return ResultVo.success(userAccountList);
        return userAccountService.getTotalRow().toString();
    }
}
