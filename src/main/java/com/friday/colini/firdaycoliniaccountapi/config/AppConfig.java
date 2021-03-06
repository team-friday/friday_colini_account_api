package com.friday.colini.firdaycoliniaccountapi.config;

import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
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
            @Autowired
            AppProperties appProperties;

            @Override
            public void run(ApplicationArguments args) {
                setFixtureAccount("admin", appProperties.getAdminId(), appProperties.getAdminPassword(), RoleType.ADMIN);
                setFixtureAccount("user", appProperties.getUserId(), appProperties.getUserPassword(), RoleType.USER);
            }
            private void setFixtureAccount(String userName, String email, String password, RoleType role) {
                Account account = Account.builder()
                        .userName(userName)
                        .email(email)
                        .password(password)
                        .roles(new HashSet<>(Arrays.asList(role)))
                        .build();
                accountService.signUp(account);
            }
        };
    }
}
