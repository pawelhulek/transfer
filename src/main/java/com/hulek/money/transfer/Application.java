package com.hulek.money.transfer;

import com.hulek.money.transfer.api.Transfers;

public class Application {
    private final Transfers transfers;

    public Application(Transfers transfersAPI) {
        transfers = transfersAPI;
    }

    public static void main(String... args) {
        new Configuration().application().start();
    }

    private void start() {
        transfers.api();
    }
}
