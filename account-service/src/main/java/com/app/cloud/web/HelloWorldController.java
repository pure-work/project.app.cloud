package com.app.cloud.web;

import com.app.cloud.web.feign.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo/")
public class HelloWorldController {

    @Autowired
    private Account account;

    @RequestMapping(value = "/add")
    public String add(Integer a, Integer b) {
        return "account result is "+account.add(a, b)
                +",consumer random is "+Math.random();
    }
}
