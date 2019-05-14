package com.friday.colini.firdaycoliniaccountapi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.friday.colini.firdaycoliniaccountapi.common.TestDescription;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import com.friday.colini.firdaycoliniaccountapi.dto.AccountDto;
import com.friday.colini.firdaycoliniaccountapi.service.CustomUserDetailsService;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Arrays;
import java.util.HashSet;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@Transactional
public class AccountControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    CustomUserDetailsService customUserDetailsService;

    @Test
    @TestDescription("id로 계정 조회")
    public void findById_Success() throws Exception {
        long id = 1;

        signUp_success();

        mockMvc.perform(get("/account/users/" + id)
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("userName").value("juyoung"));
    }

    @Test
    @TestDescription("입력값이 들어오지 않는 경우 에러 발생한다")
    public void signUp_Bad_Request_Empty_Input() throws Exception {
        AccountDto.SignUpReq account = AccountDto.SignUpReq.builder().build();

        mockMvc.perform(post("/account/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(account)))
                .andDo(print())
                .andExpect(status().isBadRequest())
                ;
    }

    @Test
    @TestDescription("입력값이 잘못 들어온 경우 에러 발생한다")
    public void signUp_Bad_Request_Wrong_Input() throws Exception {
        AccountDto.SignUpReq account = AccountDto.SignUpReq.builder()
                .userName("")
                .email("juyoung@email.com")
                .password("password")
                .mailYn(false)
                .roles(new HashSet<>(Arrays.asList(RoleType.USER)))
                .build();

        mockMvc.perform(post("/account/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(account)))
                .andDo(print())
                .andExpect(status().isBadRequest())
        ;
    }

    @Test
    @TestDescription("계정등록 성공")
    public void signUp_success() throws Exception {
        String userName = "juyoung";
        AccountDto.SignUpReq account = AccountDto.SignUpReq.builder()
                .userName(userName)
                .email("juyoung@email.com")
                .password("password")
                .mailYn(false)
                .roles(new HashSet<>(Arrays.asList(RoleType.USER)))
                .build();

//        given(accountService.signUp(any())).willReturn(account.toEntity());

        mockMvc.perform(post("/account/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .accept(MediaType.APPLICATION_JSON_UTF8_VALUE)
                .content(objectMapper.writeValueAsString(account)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("id").value(Matchers.not(0)))
                .andExpect(jsonPath("userName").value(userName))
        ;
    }
}