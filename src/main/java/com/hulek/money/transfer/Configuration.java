package com.hulek.money.transfer;

import com.google.gson.Gson;
import com.hulek.money.transfer.api.PostTransfersRoute;
import com.hulek.money.transfer.api.Transfers;
import spark.Route;

public class Configuration {

    public Application application() {
        return new Application(transfersAPI());
    }

    private Transfers transfersAPI() {
        return new Transfers(postTransfersRoute());
    }

    private Route postTransfersRoute() {
        return new PostTransfersRoute(gson());
    }

    private Gson gson() {
        return new Gson();
    }
}
