package com.hulek.money.transfer.api;

import com.google.gson.Gson;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.Repository;
import spark.Request;
import spark.Response;
import spark.Route;

import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

public class PostRoute<T> implements Route {
    private final Gson gson;
    private final Repository<T> repository;
    private final Class<T> clazz;
    private final String resourcePath;

    public PostRoute(Gson gson,
                     Repository<T> repository,
                     Class<T> clazz, String resourcePath) {
        this.gson = gson;
        this.repository = repository;
        this.clazz = clazz;
        this.resourcePath = resourcePath;
    }

    @Override
    public Object handle(Request request, Response response) {
        T tUnique = gson.fromJson(request.body(), clazz);
        if (tUnique == null) {
            throw new IllegalArgumentException("Please send Transfer in proper format!");
        }
        UUID uuid = UUID.randomUUID();
        repository.save(new Unique<>(uuid.toString(), tUnique));
        response.status(HttpServletResponse.SC_CREATED);
        response.header("Location", "/" + resourcePath + "/" + uuid);
        return "";
    }
}
