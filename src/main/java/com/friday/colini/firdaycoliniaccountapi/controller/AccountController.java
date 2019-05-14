package com.friday.colini.firdaycoliniaccountapi.controller;

import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.dto.AccountDto;
import com.friday.colini.firdaycoliniaccountapi.service.CustomUserDetailsService;
import jdk.nashorn.internal.objects.annotations.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = "/account/users", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
@RequiredArgsConstructor
public class AccountController {

    private final CustomUserDetailsService customUserDetailsService;

    @GetMapping
    public String succes(){
        return "login success";
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountDto.Res signUp(@RequestBody @Valid AccountDto.SignUpReq accountReq){
        Account account = customUserDetailsService.signUp(accountReq);
        return AccountDto.Res.of(account);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Account getAccount(@PathVariable long id){
        Account account = customUserDetailsService.search(id);
        return account;
    }

}
