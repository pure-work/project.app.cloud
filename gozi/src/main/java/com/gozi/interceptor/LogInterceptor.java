package com.gozi.interceptor;

import com.gozi.core.base.util.JsonUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class LogInterceptor implements HandlerInterceptor {

	private static Logger logger = LoggerFactory.getLogger(LogInterceptor.class);

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		String sessionId = request.getSession().getId();
		//logger.info("请求路径[" + sessionId + "][" + request.getRequestURI() + "] ");
		logger.info(
				  "SESSION[" + sessionId + "]"
				+ "TOKEN[" + request.getHeader("token") + "]"
				+ "URL[" + request.getRequestURI() + "]"
				+ "PARAM[" + JsonUtils.toJson(request.getParameterMap()) + "]"
		);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,ModelAndView modelAndView) {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex){
	}
	
}
