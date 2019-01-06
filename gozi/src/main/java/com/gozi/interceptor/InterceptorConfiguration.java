package com.gozi.interceptor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
public class InterceptorConfiguration extends WebMvcConfigurationSupport {
    @Autowired
    private LogInterceptor logInterceptor;
    @Autowired
    private TokenInterceptor tokenInterceptor;
    @Autowired
    private HeaderInterceptor headerInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //需进行日志记录拦截
        registry.addInterceptor(logInterceptor)
                .addPathPatterns("/api/**");
        //需进行header拦截
        registry.addInterceptor(headerInterceptor)
                .addPathPatterns("/api/**");
        //需进行token校验拦截
        registry.addInterceptor(tokenInterceptor)
                .addPathPatterns("/api/user/**").excludePathPatterns("/api/user/register").excludePathPatterns("/api/user/login")
                .addPathPatterns("/api/tk/**");

    }
    //静态文件处理
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("swagger-ui.html")
                .addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**")
                .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

}
