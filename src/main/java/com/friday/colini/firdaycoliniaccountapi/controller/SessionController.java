package com.friday.colini.firdaycoliniaccountapi.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class SessionController {

    @GetMapping("/hello")
    public String hello(){
        return "No login";
    }

    @GetMapping("/success")
    public String loginSuccess(){
        return "login success";
    }
}
