package com.app.cloud.web.hystrix;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value="account")
public interface AccountClient {
    @RequestMapping(value = "/add")
    String add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b);

}