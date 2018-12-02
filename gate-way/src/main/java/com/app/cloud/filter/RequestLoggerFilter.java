package com.app.cloud.filter;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 记录请求地址和请求参数过滤器
*/
public class RequestLoggerFilter extends ZuulFilter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public String filterType() {
        return "pre";
    }

    public int filterOrder() {
        return 0;
    }

    public boolean shouldFilter() {
        return true;
    }

    public Object run() {
        //accessToken 为空时不进行路由
        RequestContext ctx = RequestContext.getCurrentContext();
        HttpServletRequest request = ctx.getRequest();
        //Object accessToken = request.getParameter("accessToken");
        logger.info(String.format("ip %s %s request to %s", this.getIpAddress(request), request.getMethod(), request.getRequestURL().toString()));
        logger.info(String.format("ip %s %s request to %s", request.getRemoteHost() , request.getMethod(), request.getRequestURL().toString()));
        Enumeration<String> paramNames = request.getParameterNames();
        Map<String,Object> paramMap = new HashMap<>();
        while(paramNames.hasMoreElements()){
            String paramName = paramNames.nextElement();
            paramMap.put(paramName, request.getParameter(paramName));
        }
        logger.info(String.format("request param is %s", paramMap.toString()));
        //logger.info("access token ok,access token is "+accessToken);
        /*
        if(accessToken == null) {
            logger.warn("access token is empty , host is haven`t implemented");
            ctx.setSendZuulResponse(false);//不进行路由
            ctx.setResponseBody("access token is empty,please check it");
            ctx.setResponseStatusCode(401);//设置响应码
            return null;
        }
        */
        return null;
    }
    /**
     * 获取Ip地址
     * @param request
     * @return
     */
    public  String getIpAddress(HttpServletRequest request) {

        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

}