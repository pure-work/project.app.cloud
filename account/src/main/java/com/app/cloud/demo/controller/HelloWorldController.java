package com.app.cloud.demo.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(value="DemoAction", description = "示例行为控制器")
public class HelloWorldController {

    private final Logger logger = Logger.getLogger(getClass());

    @Autowired
    private DiscoveryClient client;

    @RequestMapping(value = "/add")
    @ApiOperation(value="加法" , httpMethod="GET", notes="通用接口")
    public Object plus(@RequestParam Integer a, @RequestParam Integer b) {
        ServiceInstance instance = client.getLocalServiceInstance();
        Integer r = a + b;
        logger.info("/plus, host:" + instance.getHost() + ", serviceId:" + instance.getServiceId() + ", result:" + r);
        return "/plus, host:" + instance.getHost() + ", serviceId:" + instance.getServiceId() + ", result:" + r;
    }

    @RequestMapping(value = "/hello")
    @ApiOperation(value="打个招呼" , httpMethod="GET", notes="通用接口")
    public Object helloWorld() {
        ServiceInstance instance = client.getLocalServiceInstance();
        logger.info("/helloWorld, host:" + instance.getHost() + ", serviceId:" + instance.getServiceId());
        return "/helloWorld, host:" + instance.getHost() + ", serviceId:" + instance.getServiceId() + ","+ "Hello World";
    }

}