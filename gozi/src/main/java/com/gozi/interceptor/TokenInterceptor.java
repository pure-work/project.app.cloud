package com.gozi.interceptor;

import com.gozi.core.base.dto.ResultVo;
import com.gozi.core.base.dto.UserToken;
import com.gozi.core.base.dto.UserTokenHolder;
import com.gozi.core.base.enums.ResultCode;
import com.gozi.core.base.redis.RedisConstant;
import com.gozi.core.base.util.JsonUtils;
import com.gozi.core.base.util.RedisUtil;
import com.gozi.core.base.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class TokenInterceptor implements HandlerInterceptor {
	private static Logger logger = LoggerFactory.getLogger(TokenInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		String token = "";
		if(StringUtil.isNotBlank(request.getHeader("Token"))) {
			token = request.getHeader("Token");
		}else if(StringUtil.isNotBlank(request.getHeader("token"))){
			token = request.getHeader("token");
		}else if(StringUtil.isNotBlank(request.getParameter("Token"))){
			token = request.getParameter("Token");
		}else if(StringUtil.isNotBlank(request.getParameter("token"))){
			token = request.getParameter("token");
		}
		if(StringUtil.isBlank(token)) {
			returnJson(response,JsonUtils.toJson(ResultVo.error(ResultCode.TOKEN_EXPIRED)));
		}
		String key =  String.format(RedisConstant.TOKEN_KEY, token);
		if(!RedisUtil.hasKey(key)){
			returnJson(response,JsonUtils.toJson(ResultVo.error(ResultCode.TOKEN_EXPIRED)));
			return false;
		}
		//持有UserToken
		UserTokenHolder.setUserToken((UserToken) RedisUtil.get(key));
		request.setAttribute("token", token);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView ){
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
		UserTokenHolder.remove();
	}
	
	private void returnJson(HttpServletResponse response, String json) {
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=utf-8");
        try {
            writer = response.getWriter();
            writer.print(json);

        } catch (IOException e) {
            logger.error("response error",e);
        } finally {
            if (writer != null)
                writer.close();
        }
    }
	
}
