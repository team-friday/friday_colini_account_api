package com.friday.colini.firdaycoliniaccountapi.config;

import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import com.friday.colini.firdaycoliniaccountapi.repository.AccountRepository;
import com.friday.colini.firdaycoliniaccountapi.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;

@Component
public class AppConfig {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();  // bcrypt
    }

    @Bean
    public ApplicationRunner runner(){
        return new ApplicationRunner() {
            @Autowired
            AccountService accountService;

            @Override
            public void run(ApplicationArguments args) {
                Account account = Account.builder()
                        .userName("admin")
                        .password("pass")
                        .email("admin@email.com")
                        .roles(new HashSet<>(Arrays.asList(RoleType.values())))
                        .build();
                accountService.signUp(account);
            }
        };
    }
}
