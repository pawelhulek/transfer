package com.hulek.money.transfer;

import com.hulek.money.transfer.api.Transfers;

public class Configuration {

    public Application application(){
        return new Application(transfersAPI());
    }
    private Transfers transfersAPI(){
        return new Transfers();
    }
}
