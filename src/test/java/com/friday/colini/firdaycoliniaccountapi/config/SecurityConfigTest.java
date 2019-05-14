package com.friday.colini.firdaycoliniaccountapi.config;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class SecurityConfigTest {

    @Autowired
    MockMvc mockMvc;
    
    @Test
    public void has_JWT_Token_Then_Connected_Resource() throws Exception {
//        mockMvc.perform(post("/account/users");
    }

    public String obtainJwt() throws Exception {
        return JWT.create()
                .withIssuer("friday")
                .sign(Algorithm.HMAC512("HS123"));
    }
}