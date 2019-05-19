package com.hulek.money.transfer;

import com.hulek.money.transfer.api.AccountsApi;
import com.hulek.money.transfer.api.TransfersApi;

public class Application {
    private final TransfersApi transfersApi;
    private final AccountsApi accountsApi;

    public Application(TransfersApi transfersAPI, AccountsApi accountsApi) {
        transfersApi = transfersAPI;
        this.accountsApi = accountsApi;
    }

    public static void main(String... args) {
        Configuration configuration = new Configuration();
        configuration.application().start();
        //TODO: think how to prepare data
        if (args != null) configuration.initializeData();
    }

    private void start() {
        transfersApi.api();
        accountsApi.api();
    }
}
