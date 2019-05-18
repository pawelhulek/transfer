package com.hulek.money.transfer.api;

import spark.Route;
import spark.Spark;

public class Transfers {
    private final Route postTransfersRoute;

    public Transfers(Route postTransfersRoute) {
        this.postTransfersRoute = postTransfersRoute;
    }

    public void api() {
        Spark.path("/transfers", () -> {
            Spark.post("", postTransfersRoute);
        });
    }
}
