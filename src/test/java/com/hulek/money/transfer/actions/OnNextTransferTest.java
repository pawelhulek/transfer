package com.hulek.money.transfer.actions;

import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.Repository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class OnNextTransferTest {
    Repository<Transfer> transferRepository;
    OnNextTransfer onNextTransfer;
    ArgumentCaptor<Unique> uniqueArgumentCaptor = ArgumentCaptor.forClass(Unique.class);

    @BeforeEach
    void setUp() {
        transferRepository = mock(Repository.class);
        onNextTransfer = new OnNextTransfer(transferRepository);

    }

    @Test
    void updateTransfer() {
        onNextTransfer.call(new Unique<>("ss", transfer()));
        verify(transferRepository).save(uniqueArgumentCaptor.capture());
        assertEquals(new Unique<>("ss", transfer().complete()), uniqueArgumentCaptor.getValue());
    }

    @Test
    void dontUpdateCompletedTransfer() {
        onNextTransfer.call(new Unique<>("ss", transfer().complete()));
        verifyZeroInteractions(transferRepository);
    }


    private Transfer transfer() {
        return new Transfer("a", "b", BigDecimal.ONE, "PLN");
    }
}