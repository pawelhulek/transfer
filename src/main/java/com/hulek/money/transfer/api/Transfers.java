package com.hulek.money.transfer.api;

import spark.Route;
import spark.Spark;

public class Transfers {
    private final Route postTransfersRoute;
    private final Route getTransfersRoute;

    public Transfers(Route postTransfersRoute, Route getTransfersRoute) {
        this.postTransfersRoute = postTransfersRoute;
        this.getTransfersRoute = getTransfersRoute;
    }

    public void api() {
        Spark.path("/transfers", () -> {
            Spark.before("",
                    (request, response) -> System.out.println(request.url()));
            Spark.get("/:id", getTransfersRoute);
            Spark.post("", postTransfersRoute);
        });
    }
}
