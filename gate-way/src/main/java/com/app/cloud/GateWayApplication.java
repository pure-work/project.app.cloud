package com.app.cloud;

import com.app.cloud.filter.AccessTokenFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.Bean;

@EnableZuulProxy
@SpringBootApplication
public class GateWayApplication {

    public static void main(String[] args) {

        SpringApplication.run(GateWayApplication.class, args);

    }
    //添加过滤器
    @Bean
    public AccessTokenFilter accessTokenFilter() {
        return new AccessTokenFilter();
    }

}
