package com.app.cloud.web.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;

//@Component
public class AccountClientHystrix {

    //@Override
    public String add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
        return "add method call failed";
    }

}