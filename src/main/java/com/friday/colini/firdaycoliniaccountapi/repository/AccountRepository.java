package com.friday.colini.firdaycoliniaccountapi.repository;

import com.friday.colini.firdaycoliniaccountapi.domain.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);
}
