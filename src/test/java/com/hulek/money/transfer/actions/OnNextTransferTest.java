package com.hulek.money.transfer.actions;

import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.TransfersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class OnNextTransferTest {
    TransfersRepository repository;
    OnNextTransfer onNextTransfer;
    ArgumentCaptor<Unique> uniqueArgumentCaptor = ArgumentCaptor.forClass(Unique.class);

    @BeforeEach
    void setUp() {
        repository = mock(TransfersRepository.class);
        onNextTransfer = new OnNextTransfer(repository);

    }

    @Test
    void updateTransfer() {
        onNextTransfer.call(new Unique<>("ss", transfer()));
        verify(repository).save(uniqueArgumentCaptor.capture());
        assertEquals(new Unique<>("ss", transfer().complete()), uniqueArgumentCaptor.getValue());
    }

    private Transfer transfer() {
        return new Transfer("a", "b", BigDecimal.ONE, "PLN");
    }
}