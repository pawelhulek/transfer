package com.hulek.money.transfer.repository;

import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.SubmissionPublisher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class TransfersApiCacheRepositoryTest {
    CacheRepository cacheRepository;

    Map<String, Transfer> cache;
    private SubmissionPublisher<Unique<Transfer>> publisher;

    @BeforeEach
    public void setUp() {
        cache = new HashMap<>();
        publisher = mock(SubmissionPublisher.class);
        cacheRepository = new CacheRepository(cache, publisher);
    }

    @Test
    void save() {
        cacheRepository.save(new Unique<>("asd", transfer()));
        assertEquals(transfer(), cache.get("asd"));
    }

    @Test
    void publish() {
        cacheRepository.save(new Unique<>("asd", transfer()));
        verify(publisher).submit(eq(new Unique<>("asd", transfer())));
    }

    private Transfer transfer() {
        return new Transfer("a", "b", BigDecimal.ONE, "USD");
    }
}