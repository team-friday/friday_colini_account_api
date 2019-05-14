package com.friday.colini.firdaycoliniaccountapi.service;

import com.friday.colini.firdaycoliniaccountapi.common.TestDescription;
import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import com.friday.colini.firdaycoliniaccountapi.dto.AccountDto;
import com.friday.colini.firdaycoliniaccountapi.repository.AccountRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Optional;

import static com.friday.colini.firdaycoliniaccountapi.AccountAbstract.accountOf;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.failBecauseExceptionWasNotThrown;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AccountServiceTest {

    @InjectMocks
    AccountService accountService;
    @Mock
    PasswordEncoder passwordEncoder;
    @Mock
    AccountRepository accountRepository;
    // extendWith == auto

    @TestDescription("중복된 이메일 일 경우 에러를 반환한다")
    public void signUp_Duplicated_Email_Fail() {
    }

    @Test
    @TestDescription("회원가입 성공한다")
    public void findByUsername_Sccuccess() {
        String userName = "juyoung@email.com";
        String password = "password";

        Account expected = accountOf("juyoung", userName, passwordEncoder.encode(password));
        given(accountRepository.save(any())).willReturn(expected);

        Account newAccount = accountService.signUp(builderSignUpSeq(expected));

        assertThat(newAccount.getEmail()).isEqualTo(userName);
        assertThat(passwordEncoder.matches(password, newAccount.getPassword())).isTrue();
    }

    @Test (expected = UsernameNotFoundException.class)
    @TestDescription("등록된 계정이 아닐 시 에러")
    public void findByUserName_Fail() {
        String userName = "nonEmail@Email.com";
        given(accountRepository.findByEmail(userName)).willReturn(Optional.empty());
        accountService.loadUserByUsername(userName);
    }


    private AccountDto.SignUpReq builderSignUpSeq(Account account) {
        return AccountDto.SignUpReq.builder()
                .email(account.getEmail())
                .userName(account.getUserName())
                .password(account.getPassword())
                .roles(account.getRoles())
                .build();
    }

}