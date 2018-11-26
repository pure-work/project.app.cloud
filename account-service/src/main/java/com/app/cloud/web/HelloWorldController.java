package com.app.cloud.web;

import com.app.cloud.web.feign.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

    @Autowired
    private Account account;

    @RequestMapping(value = "/demoAdd")
    public String add(@RequestParam(value = "a") Integer a, @RequestParam(value = "b") Integer b) {
        return "accountClient result is "+account.add(a, b)
                +",consumerClient random is "+Math.random();
    }
}
