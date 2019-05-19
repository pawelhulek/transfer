package com.hulek.money.transfer.api;

import spark.Route;
import spark.Spark;

public class TransfersApi {
    private final Route postTransfersRoute;
    private final Route getTransfersRoute;

    public TransfersApi(Route postTransfersRoute, Route getTransfersRoute) {
        this.postTransfersRoute = postTransfersRoute;
        this.getTransfersRoute = getTransfersRoute;
    }

    public void api() {
        Spark.path("/transfers", () -> {
            Spark.get("/:id", getTransfersRoute);
            Spark.post("", postTransfersRoute);
        });
    }
}
