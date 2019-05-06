package com.friday.colini.firdaycoliniaccountapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccountController {

    @GetMapping("/api/hello")
    public String index() {
        return "security filter pass";
    }

    @GetMapping("/api/hello2")
    public String index2() {
        return "security filter fail";
    }

    @GetMapping("/login/hello")
    public String loginSuccess() {
        return "login success";
    }
}
