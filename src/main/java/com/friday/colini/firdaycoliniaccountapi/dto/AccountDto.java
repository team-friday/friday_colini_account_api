package com.friday.colini.firdaycoliniaccountapi.dto;


import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import com.friday.colini.firdaycoliniaccountapi.domain.RoleType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class AccountDto {

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    public static class SignUpReq{
        @Email(message = "이메일 형식에 맞지 않습니다")
        private String email;
        @NotEmpty(message = "password is wrong")
        private String password;
        @NotEmpty(message = "userName is wrong")
        private String userName;
        @NotEmpty(message = "role is wrong")
        private RoleType role;
        private boolean mailYn;

        @Builder
        public SignUpReq(@NotEmpty String email,
                         @NotEmpty String password,
                         @NotEmpty String userName,
                         @NotEmpty RoleType role,
                         boolean mailYn) {
            this.email = email;
            this.password = password;
            this.userName = userName;
            this.role = role;
            this.mailYn = mailYn;
        }

        public Account toEntity(){
            return Account.builder()
                    .userName(userName)
                    .email(email)
                    .password(password)
                    .role(role)
                    .mailYn(mailYn)
                    .status(true)
                    .build();
        }

        public void encodePassword(PasswordEncoder passwordEncoder, String password){
            this.password = passwordEncoder.encode(password);
        }
    }

    @Getter
    public static class Res {
        private Long id;
        private String email;
        private String password;
        private String userName;
        private Set<RoleType> roles;
        private boolean mailYn;
        private boolean status;
        private LocalDateTime createAt;
        private LocalDateTime updateAt;

        @Builder
        public Res(Long id,
                   String email,
                   String password,
                   String userName,
                   Set<RoleType> roles,
                   boolean mailYn,
                   boolean status,
                   LocalDateTime createAt,
                   LocalDateTime updateAt) {
            this.id = id;
            this.email = email;
            this.password = password;
            this.userName = userName;
            this.roles = roles;
            this.mailYn = mailYn;
            this.status = status;
            this.createAt = createAt;
            this.updateAt = updateAt;
        }

        public static Res of(Account account){
             return Res.builder()
                     .id(account.getId())
                     .email(account.getEmail())
                     .password(account.getPassword())
                     .userName(account.getUserName())
                     .mailYn(account.isMailYn())
                     .status(account.isStatus())
                     .roles(new HashSet<>(Arrays.asList(account.getRole())))
                     .createAt(account.getCreateAt())
                     .updateAt(account.getCreateAt())
                     .build();
        }
    }
}
