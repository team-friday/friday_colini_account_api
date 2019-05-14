package com.friday.colini.firdaycoliniaccountapi;

import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import org.junit.Ignore;

import java.util.Arrays;
import java.util.HashSet;

@Ignore
public class AccountAbstract {

    public static Account accountOf(String userName, String email,
                                    String password) {
        return Account.builder()
                .userName(userName)
                .email(email)
                .password(password)
                .status(true)
                .roles(new HashSet<>(Arrays.asList(RoleType.values())))
                .build();
    }

}
