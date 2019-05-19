package com.hulek.money.transfer.api;

import com.google.gson.Gson;
import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.TransfersRepository;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class PostTransfersRoute implements Route {
    private final Gson gson;
    private final TransfersRepository repository;

    public PostTransfersRoute(Gson gson, TransfersRepository repository) {
        this.gson = gson;
        this.repository = repository;
    }

    @Override
    public Object handle(Request request, Response response) {
        Transfer transfer = gson.fromJson(request.body(), Transfer.class);
        if (transfer == null) {
            throw new IllegalArgumentException("Please send Transfer in proper format!");
        }
        UUID uuid = UUID.randomUUID();
        repository.save(new Unique<>(uuid.toString(), transfer));
        response.status(HttpServletResponse.SC_CREATED);
        response.header("Location", "/transfers/" + uuid);
        return "";
    }
}
