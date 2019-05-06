package com.friday.colini.firdaycoliniaccountapi.config;

import com.friday.colini.firdaycoliniaccountapi.common.TestDescription;
import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import com.friday.colini.firdaycoliniaccountapi.service.AccountService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthServerConfigTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    AccountService accountService;

    @Test
    @TestDescription("인증 토큰 발급 받는데 성공한다")
    public void createToken_Success() throws Exception {

        String userName = "juyoung@email.com";
        String password = "password";
        Account account = Account.builder()
                .email(userName)
                .userName("juyoung")
                .password(password)
                .roles(new HashSet<>(Arrays.asList(RoleType.values())))
                .build();

        accountService.signUp(account);

        mockMvc.perform(post("/oauth/token")
                    .with(httpBasic("myApp","pass")) // basic auth 생성
                    .param("username", userName)
                    .param("password", password)
                    .param("grant_type", "password")
                )
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("access_token").exists());


    }
}