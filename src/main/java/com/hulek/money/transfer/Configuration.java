package com.hulek.money.transfer;

import com.google.gson.Gson;
import com.hulek.money.transfer.actions.OnNextCompletedTransfer;
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

    public Repository<Account> accountsRepository() {
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


    public Application application(Repository<Transfer> transfersRepository, Repository<Account> accountsRepository) {
        return new Application(transfersAPI(transfersRepository), accountsApi(accountsRepository));
    }

    private AccountsApi accountsApi(Repository<Account> accountsRepository) {
        return new AccountsApi(accountsGetRoute(accountsRepository));
    }

    private Route accountsGetRoute(Repository<Account> accountsRepository) {
        return new GetRoute<Account>(gson(), accountsRepository);
    }

    private TransfersApi transfersAPI(Repository<Transfer> transfersRepository) {
        return new TransfersApi(postTransfersRoute(transfersRepository), getTransfersRoute(transfersRepository));
    }

    private Route getTransfersRoute(Repository<Transfer> transfersRepository) {
        return new GetRoute<>(gson(), transfersRepository);
    }

    private Route postTransfersRoute(Repository<Transfer> transfersRepository) {
        return new PostRoute<>(gson(), transfersRepository, Transfer.class, "transfers");
    }

    public CacheRepository<Transfer> transfersRepository(Repository<Account> accountRepository) {
        return new CacheRepository<>(transfersCache(), tr -> transfersPublisher(accountRepository, tr));
    }

    private SubmissionPublisher<Unique<Transfer>> transfersPublisher(Repository<Account> accountRepository, Repository<Transfer> transfersRepository) {
        var publisher = new SubmissionPublisher<Unique<Transfer>>();
        publisher.subscribe(transferSubscriber(transfersRepository));
        publisher.subscribe(compltedTransferSubscriber(accountRepository));
        return publisher;
    }

    private Flow.Subscriber<Unique<Transfer>> compltedTransferSubscriber(Repository<Account> accountRepository) {
        Subscriber<Unique<Transfer>> rxSubscriber = Subscribers.create(actionCompletedTransfer(accountRepository));
        return FlowAdapters.toFlowSubscriber(RxReactiveStreams.toSubscriber(rxSubscriber));
    }

    private Action1<Unique<Transfer>> actionCompletedTransfer(Repository<Account> accountRepository) {
        return new OnNextCompletedTransfer(accountRepository);
    }

    private Flow.Subscriber<Unique<Transfer>> transferSubscriber(Repository<Transfer> transfersRepository) {

        Subscriber<Unique<Transfer>> rxSubscriber = Subscribers.create(actionTransfer(transfersRepository));

        return FlowAdapters.toFlowSubscriber(RxReactiveStreams.toSubscriber(rxSubscriber));
    }

    private Action1<Unique<Transfer>> actionTransfer(Repository<Transfer> transfersRepository) {
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

    public void initializeData(Repository<Account> accountsRepository) {
        new Preloader(accountsRepository).preLoadData();
    }
}
