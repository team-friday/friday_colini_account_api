package com.friday.colini.firdaycoliniaccountapi.service;

import com.friday.colini.firdaycoliniaccountapi.common.TestDescription;
import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import com.friday.colini.firdaycoliniaccountapi.repository.AccountRepository;
import com.sun.tools.classfile.Opcode;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class AccountServiceTest {

    @Autowired
    AccountService accountService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @TestDescription("등록된 계정")
    public void findByUsername_Sccuccess() {
        String userName = "juyoung@email.com";
        String password = "password";
        Account account = Account.builder()
                .email(userName)
                .userName("juyoung")
                .password(password)
                .roles(new HashSet<>(Arrays.asList(RoleType.values())))
                .build();

        accountService.signUp(account);

        UserDetailsService userDetailsService = accountService;
        UserDetails userDetails = userDetailsService.loadUserByUsername(userName);
        assertThat(userDetails.getUsername()).isEqualTo(userName);
        assertThat(passwordEncoder.matches(password, userDetails.getPassword())).isTrue();
    }

    @Test (expected = UsernameNotFoundException.class)
    @TestDescription("등록되 계정이 아닐 시 에러")
    public void findByUserName_Fail() {
        String userName = "nonEmail@Email.com";
        accountService.loadUserByUsername(userName);
    }



}