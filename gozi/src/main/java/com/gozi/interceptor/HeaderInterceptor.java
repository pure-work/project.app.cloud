package com.gozi.interceptor;

import com.gozi.core.base.util.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class HeaderInterceptor implements HandlerInterceptor {
	private static Logger logger = LoggerFactory.getLogger(HeaderInterceptor.class);
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
		String version ="";
		if(StringUtil.isNotBlank(request.getHeader("Version"))) {
			version = request.getHeader("Version");
		}else if(StringUtil.isNotBlank(request.getHeader("version"))){
			version = request.getHeader("version");
		}else if(StringUtil.isNotBlank(request.getParameter("Version"))){
			version = request.getParameter("Version");
		}else if(StringUtil.isNotBlank(request.getParameter("version"))){
			version = request.getParameter("version");
		}
		request.setAttribute("version", version);
		return true;
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
	}
	
}
