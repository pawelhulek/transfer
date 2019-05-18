package com.hulek.money.transfer.api;

import com.google.gson.Gson;
import com.hulek.money.transfer.dto.Transfer;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class PostTransfersRoute implements Route {
    private final Gson gson;

    public PostTransfersRoute(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        Transfer transfer = gson.fromJson(request.body(), Transfer.class);
        if (transfer == null) {
            throw new IllegalArgumentException("Please send Transfer in proper format!");
        }
        response.status(HttpServletResponse.SC_CREATED);
        response.header("Location", "/transfers/" + UUID.randomUUID());
        return "";
    }
}
