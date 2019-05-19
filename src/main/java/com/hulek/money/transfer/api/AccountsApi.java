package com.hulek.money.transfer.api;

import spark.Route;
import spark.Spark;

public class AccountsApi {
    private final Route getAccountsRoute;

    public AccountsApi(Route getAccountsRoute) {
        this.getAccountsRoute = getAccountsRoute;
    }

    public void api() {
        Spark.path("/accounts", () -> {
            Spark.get("/:id", getAccountsRoute);
        });
    }
}
