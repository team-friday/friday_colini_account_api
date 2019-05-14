package com.friday.colini.firdaycoliniaccountapi.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

public class SessionDto {

    @Getter
    @NoArgsConstructor
    public static class SignInReq{
        @NotEmpty
        private String userName;
        @NotEmpty
        private String password;

        @Builder
        public SignInReq(@NotEmpty String userName,
                         @NotEmpty String password) {
            this.userName = userName;
            this.password = password;
        }
    }

}
