package com.hulek.money.transfer.repository;

import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;

import java.util.Map;
import java.util.concurrent.SubmissionPublisher;

public class TransfersCacheRepository implements TransfersRepository {
    private final Map<String, Transfer> cacheMap;
    private final SubmissionPublisher<Unique<Transfer>> transferPublisher;

    public TransfersCacheRepository(Map<String, Transfer> cacheMap, SubmissionPublisher<Unique<Transfer>> transferPublisher) {
        this.cacheMap = cacheMap;
        this.transferPublisher = transferPublisher;
    }

    @Override
    public void save(Unique<Transfer> transfer) {
        cacheMap.put(transfer.getId(), transfer.getValue());
        transferPublisher.submit(transfer);
    }
}
