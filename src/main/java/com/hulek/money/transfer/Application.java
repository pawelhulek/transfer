package com.hulek.money.transfer;

import com.hulek.money.transfer.api.AccountsApi;
import com.hulek.money.transfer.api.TransfersApi;
import com.hulek.money.transfer.dto.Account;
import com.hulek.money.transfer.repository.Repository;

public class Application {
    private final TransfersApi transfersApi;
    private final AccountsApi accountsApi;

    public Application(TransfersApi transfersAPI, AccountsApi accountsApi) {
        transfersApi = transfersAPI;
        this.accountsApi = accountsApi;
    }

    public static void main(String... args) {
        Configuration configuration = new Configuration();
        Repository<Account> accountsRepository = configuration.accountsRepository();
        configuration.application(configuration.transfersRepository(accountsRepository), accountsRepository).start();
        //TODO: think how to prepare data
        if (args != null) configuration.initializeData(accountsRepository);
    }

    private void start() {
        transfersApi.api();
        accountsApi.api();
    }
}
