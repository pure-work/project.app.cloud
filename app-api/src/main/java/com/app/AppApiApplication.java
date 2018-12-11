package com.app;

import com.app.core.base.dao.BaseMapper;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.app.api.**.dao", markerInterface = BaseMapper.class)
public class AppApiApplication {

    public static void main(String[] args) {

        SpringApplication.run(AppApiApplication.class, args);

    }
}
