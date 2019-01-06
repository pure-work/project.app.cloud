package com.gozi.api.system.controller;

import com.gozi.core.base.dto.ResultVo;
import com.gozi.core.base.enums.ResultCode;
import com.gozi.core.base.redis.RedisConstant;
import com.gozi.core.base.util.DateUtil;
import com.gozi.core.base.util.RedisUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/sys/")
@Api(value="System", description = "系统公用模块")
public class SysController {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /**
     * 获取短信验证码
     */
    @RequestMapping("/getPhoneCode")
    @ApiOperation(value="获取短信验证码" , httpMethod="GET", notes="通用接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "platform", value = "平台(1.APP 2.微信)", required = true, paramType = "header", dataType = "String"),
            @ApiImplicitParam(name = "version", value = "版本", required = true, paramType = "header", dataType = "String"),
            //@ApiImplicitParam(name = "token", value = "token", required = true, paramType = "header", dataType = "String")
            @ApiImplicitParam(name = "phone", value = "手机", required = true, paramType = "query", dataType = "String")
    })
    public ResultVo getPhoneCode(HttpServletRequest request, String phone) {
        String sessionId = request.getSession().getId();
        String redisKey = String.format(RedisConstant.PHONE_CODE_SESSION_KEY, sessionId);
        if(RedisUtil.hasKey(redisKey)){
            return ResultVo.error(ResultCode.ERROR.getCode(),"您刚刚发送过短信验证码，请稍后再试");
        }else{
            Map<String,Object> value = new HashMap<>();
            value.put("phone", phone);
            value.put("second", DateUtil.getTimeInSecond(new Date()));
            RedisUtil.set(redisKey, value, RedisConstant.REDIS_MINUTE_SECONDS);
        }
        return ResultVo.error(ResultCode.ERROR.getCode(),"未集成短信验证码");
    }

}
