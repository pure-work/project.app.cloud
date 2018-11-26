package com.app.cloud.web;

import com.app.cloud.web.hystrix.AccountClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class HelloWorldController {

    @Autowired
    private AccountClient accountClient;

    @RequestMapping(value = "/demoAdd")
    public String add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
        return "accountClient result is "+accountClient.add(a, b)
                +",consumerClient random is "+Math.random();
    }
}
