package com.hulek.money.transfer.api;

import com.google.gson.Gson;
import com.hulek.money.transfer.repository.Repository;
import spark.Request;
import spark.Response;
import spark.Route;

public class GetRoute<T> implements Route {
    private final Repository<T> repository;
    private final Gson gson;

    public GetRoute(Gson gson, Repository<T> repository) {
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
