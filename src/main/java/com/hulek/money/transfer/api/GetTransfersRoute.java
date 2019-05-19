package com.hulek.money.transfer.api;

import com.google.gson.Gson;
import com.hulek.money.transfer.repository.TransfersRepository;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetTransfersRoute implements Route {
    private final TransfersRepository repository;
    private final Gson gson;

    public GetTransfersRoute(Gson gson, TransfersRepository repository) {
        this.repository = repository;
        this.gson = gson;
    }

    @Override
    public Object handle(Request request, Response response) {
        String id = request.params("id");
        var transfer = repository.getByUniqueId(id);
        response.header("Location", "/transfers/" + id);
        return gson.toJson(transfer);
    }
}
