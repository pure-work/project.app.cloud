package com.app.cloud.web.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

//@Component("accountClientFallback")
@Component
public class AccountClientFallback implements AccountClient{

    //@Override
    public String add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
        return "add method call failed";
    }

}