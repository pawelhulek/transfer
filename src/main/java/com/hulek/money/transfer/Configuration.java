package com.hulek.money.transfer;

import com.google.gson.Gson;
import com.hulek.money.transfer.actions.OnNextTransfer;
import com.hulek.money.transfer.api.AccountsApi;
import com.hulek.money.transfer.api.GetRoute;
import com.hulek.money.transfer.api.PostRoute;
import com.hulek.money.transfer.api.TransfersApi;
import com.hulek.money.transfer.dto.Account;
import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.CacheRepository;
import com.hulek.money.transfer.repository.Repository;
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

    private Repository<Transfer> transfersRepository = transfersRepository();
    private Repository<Account> accountsRepository = accountsRepository();

    private Repository<Account> accountsRepository() {
        return new CacheRepository<>(accountsCache(), accountsPublisher());
    }


    private SubmissionPublisher<Unique<Account>> accountsPublisher() {
        var publisher = new SubmissionPublisher<Unique<Account>>();
        publisher.subscribe(accountsSubscriber());
        return publisher;
    }

    private Flow.Subscriber<Unique<Account>> accountsSubscriber() {
        Subscriber<Unique<Account>> rxSubscriber = Subscribers.empty();
        return FlowAdapters.toFlowSubscriber(RxReactiveStreams.toSubscriber(rxSubscriber));
    }


    public Application application() {
        return new Application(transfersAPI(), accountsApi());
    }

    private AccountsApi accountsApi() {
        return new AccountsApi(accountsGetRoute());
    }

    private Route accountsGetRoute() {
        return new GetRoute<Account>(gson(), accountsRepository);
    }

    private TransfersApi transfersAPI() {
        return new TransfersApi(postTransfersRoute(), getTransfersRoute());
    }

    private Route getTransfersRoute() {
        return new GetRoute<>(gson(), transfersRepository);
    }

    private Route postTransfersRoute() {
        return new PostRoute<>(gson(), transfersRepository, Transfer.class, "transfers");
    }

    private CacheRepository<Transfer> transfersRepository() {
        return new CacheRepository<>(transfersCache(), transfersPublisher());
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
        return new OnNextTransfer(transfersRepository);
    }

    private Map<String, Transfer> transfersCache() {
        return new ConcurrentHashMap<>();
    }

    private Map<String, Account> accountsCache() {
        return new ConcurrentHashMap<>();
    }

    private Gson gson() {
        return new Gson();
    }

    public void initializeData() {
        new Preloader(accountsRepository).preLoadData();
    }
}
