package com.hulek.money.transfer;

import com.hulek.money.transfer.dto.Account;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.Repository;

import java.util.Collections;

public class Preloader {
    private final Repository<Account> accountRepository;

    public Preloader(Repository<Account> accountRepository) {
        this.accountRepository = accountRepository;
    }

    public void preLoadData() {
        accountRepository.save(new Unique<>("1", new Account("1", "PLN", Collections.emptyList())));
        accountRepository.save(new Unique<>("2", new Account("2", "USD", Collections.emptyList())));
    }
}
