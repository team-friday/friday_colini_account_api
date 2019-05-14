package com.friday.colini.firdaycoliniaccountapi.security;

import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class JwtTokenProviderTest {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    AuthenticationManager authenticationManager;

    @Test
    public void account_converted_UserPrincipal() {
        String password = "user";
        String email = "user@email.com";
        Account account = Account.builder()
                            .userName("user")
                            .email(email)
                            .password(password)
                            .roles(new HashSet<>(Arrays.asList(RoleType.USER)))
                            .build();

        UserPrincipal userPrincipal = UserPrincipal.create(account);

        assertThat(userPrincipal.getUserName()).isEqualTo(account.getUserName());
        assertThat(userPrincipal.getEmail()).isEqualTo(account.getEmail());
        assertThat(userPrincipal.getPassword()).isEqualTo(account.getPassword());
    }

    @Test
    public void JWT_Generater() {
        /* HashKey : startwith [HS]   */
        String jwtSecret = "HS123";

        String jwtBuild = Jwts.builder()
                .setSubject(Long.toString(2))
                .setIssuedAt(new Date())
                .setExpiration(new Date())
                .signWith(SignatureAlgorithm.HS512, jwtSecret)
                .compact();

        assertThat(jwtBuild).isNotNull();
    }
}