package com.hulek.money.transfer;

import com.google.gson.Gson;
import com.hulek.money.transfer.api.PostTransfersRoute;
import com.hulek.money.transfer.api.Transfers;
import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.TransfersCacheRepository;
import com.hulek.money.transfer.repository.TransfersRepository;
import spark.Route;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.SubmissionPublisher;

public class Configuration {

    public Application application() {
        return new Application(transfersAPI());
    }

    private Transfers transfersAPI() {
        return new Transfers(postTransfersRoute());
    }

    private Route postTransfersRoute() {
        return new PostTransfersRoute(gson(), transfersRepository());
    }

    private TransfersRepository transfersRepository() {
        return new TransfersCacheRepository(transfersCache(), transfersPublisher());
    }

    private SubmissionPublisher<Unique<Transfer>> transfersPublisher() {
        return new SubmissionPublisher<>();
    }

    private Map<String, Transfer> transfersCache() {
        return new ConcurrentHashMap<>();
    }

    private Gson gson() {
        return new Gson();
    }
}
