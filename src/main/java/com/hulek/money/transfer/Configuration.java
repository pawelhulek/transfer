package com.hulek.money.transfer;

import com.google.gson.Gson;
import com.hulek.money.transfer.actions.OnNextTransfer;
import com.hulek.money.transfer.api.GetTransfersRoute;
import com.hulek.money.transfer.api.PostTransfersRoute;
import com.hulek.money.transfer.api.Transfers;
import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.TransfersCacheRepository;
import com.hulek.money.transfer.repository.TransfersRepository;
import org.reactivestreams.FlowAdapters;
import rx.RxReactiveStreams;
import rx.Subscriber;
import rx.functions.Action1;
import rx.observers.Subscribers;
import spark.Route;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

public class Configuration {

    private TransfersRepository transfersRepository = transfersRepository();

    public Application application() {
        return new Application(transfersAPI());
    }

    private Transfers transfersAPI() {
        return new Transfers(postTransfersRoute(), getTransfersRoute());
    }

    private Route getTransfersRoute() {
        return new GetTransfersRoute(gson(), transfersRepository);
    }

    private Route postTransfersRoute() {
        return new PostTransfersRoute(gson(), transfersRepository);
    }

    private TransfersRepository transfersRepository() {
        return new TransfersCacheRepository(transfersCache(), transfersPublisher());
    }

    private SubmissionPublisher<Unique<Transfer>> transfersPublisher() {
        var publisher = new SubmissionPublisher<Unique<Transfer>>();
        publisher.subscribe(transferSubscriber());
        return publisher;
    }

    private Flow.Subscriber<Unique<Transfer>> transferSubscriber() {
        Subscriber<Unique<Transfer>> rxSubscriber = Subscribers.create(actionTransfer());
        return FlowAdapters.toFlowSubscriber(RxReactiveStreams.toSubscriber(rxSubscriber));
    }

    private Action1<Unique<Transfer>> actionTransfer() {
        return new OnNextTransfer();
    }

    private Map<String, Transfer> transfersCache() {
        return new ConcurrentHashMap<>();
    }

    private Gson gson() {
        return new Gson();
    }
}
