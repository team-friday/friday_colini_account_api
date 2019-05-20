package com.friday.colini.firdaycoliniaccountapi.controller;

import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.dto.AccountDto;
import com.friday.colini.firdaycoliniaccountapi.repository.AccountRepository;
import com.friday.colini.firdaycoliniaccountapi.security.CurrentUser;
import com.friday.colini.firdaycoliniaccountapi.security.UserPrincipal;
import com.friday.colini.firdaycoliniaccountapi.service.CustomUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping(value = "/account/users")
@RequiredArgsConstructor
public class AccountController {

    private final CustomUserDetailsService customUserDetailsService;

    @Autowired
    AccountRepository accountRepository;

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

    @GetMapping("/me")
    public Account getAccount(@CurrentUser UserPrincipal userPrincipal) {
        Account account = accountRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new IllegalArgumentException());
        return account;
    }

}
