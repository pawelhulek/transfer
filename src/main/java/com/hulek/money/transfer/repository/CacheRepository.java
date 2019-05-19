package com.hulek.money.transfer.repository;

import com.hulek.money.transfer.dto.Unique;

import java.util.Map;
import java.util.concurrent.SubmissionPublisher;

public class CacheRepository<T> implements Repository<T> {
    private final Map<String, T> cacheMap;
    private final SubmissionPublisher<Unique<T>> transferPublisher;

    public CacheRepository(Map<String, T> cacheMap, SubmissionPublisher<Unique<T>> transferPublisher) {
        this.cacheMap = cacheMap;
        this.transferPublisher = transferPublisher;
    }

    @Override
    public void save(Unique<T> transfer) {
        cacheMap.put(transfer.getId(), transfer.getValue());
        transferPublisher.submit(transfer);
    }

    @Override
    public T getByUniqueId(String id) {
        return cacheMap.get(id);
    }
}
