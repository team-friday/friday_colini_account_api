package com.friday.colini.firdaycoliniaccountapi.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class AuthProviderTest {

    @Test
    public void authProvider_return() {
        String registeredId = "google";

        AuthProvider authProvider = AuthProvider.valueOf(registeredId);

        assertThat(authProvider.name()).isEqualTo(registeredId);
    }
}