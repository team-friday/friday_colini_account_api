package com.friday.colini.firdaycoliniaccountapi.controller;

import com.friday.colini.firdaycoliniaccountapi.dto.JwtAuthenticationResponse;
import com.friday.colini.firdaycoliniaccountapi.dto.SessionDto;
import com.friday.colini.firdaycoliniaccountapi.security.JwtTokenProvider;
import com.friday.colini.firdaycoliniaccountapi.security.UserPrincipal;
import com.friday.colini.firdaycoliniaccountapi.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/session")
public class SessionController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    CustomUserDetailsService customUserDetailsService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @GetMapping
    public String loginPage() {
        return "login page";
    }

    @PostMapping("/sign-in")
    @ResponseStatus(HttpStatus.OK)
    public JwtAuthenticationResponse signIn(@RequestBody SessionDto.SignInReq signInReq) {
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(signInReq.getUserName(), signInReq.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
        // todo : UserPrincipal -> Account

        // todo : history

        String accessToken = tokenProvider.generateToken(userPrincipal);
        return new JwtAuthenticationResponse(accessToken);
    }
}

