package com.hulek.money.transfer.api;

import java.util.UUID;

import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static spark.Spark.*;

public class Transfers {
    public void api() {
        path("/transfers", () -> {
            post("", (rq, rs) -> {
                rs.status(SC_CREATED);
                rs.header("Location", "/transfers/" + UUID.randomUUID());
                return "";
            });
        });

    }
}
