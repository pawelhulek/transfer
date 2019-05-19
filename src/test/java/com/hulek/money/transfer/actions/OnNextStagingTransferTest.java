package com.hulek.money.transfer.actions;

import com.hulek.money.transfer.dto.Account;
import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.Repository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

class OnNextStagingTransferTest {
    private Repository<Account> accountRepository = mock(Repository.class);
    private Repository<Transfer> transferRepository = mock(Repository.class);

    OnNextStagingTransfer onNextStagingTransfer = new OnNextStagingTransfer(accountRepository, transferRepository);

    @Test
    void saveNewAccount() {
        when(accountRepository.getByUniqueId("1")).thenReturn(new Account("1", "PLN", emptyList()));
        when(accountRepository.getByUniqueId("2")).thenReturn(new Account("2", "PLN", emptyList()));
        onNextStagingTransfer.call(new Unique<>("a", transfer().staging()));
        verify(accountRepository, atLeastOnce()).save(any());
    }

    @Test
    void dontSaveNotStaged() {
        when(accountRepository.getByUniqueId("1")).thenReturn(new Account("1", "PLN", emptyList()));
        when(accountRepository.getByUniqueId("2")).thenReturn(new Account("2", "PLN", emptyList()));
        onNextStagingTransfer.call(new Unique<>("a", transfer()));
        verifyZeroInteractions(accountRepository);
    }

    @Test
    void findFirstAccount() {
        when(accountRepository.getByUniqueId("1")).thenReturn(new Account("1", "PLN", emptyList()));
        when(accountRepository.getByUniqueId("2")).thenReturn(new Account("2", "PLN", emptyList()));
        onNextStagingTransfer.call(new Unique<>("a", transfer().staging()));
        verify(accountRepository).getByUniqueId("1");
    }

    @Test
    void saveFirstAccountWithTransaction() {
        when(accountRepository.getByUniqueId("1")).thenReturn(new Account("1", "PLN", emptyList()));
        when(accountRepository.getByUniqueId("2")).thenReturn(new Account("2", "PLN", emptyList()));

        onNextStagingTransfer.call(new Unique<>("a", transfer().staging()));
        verify(accountRepository).save(new Unique<>("1", new Account("1", "PLN", List.of(transfer().completed()))));
    }

    @Test
    void saveSecondAccountWithTransaction() {
        when(accountRepository.getByUniqueId("1")).thenReturn(new Account("1", "PLN", emptyList()));
        when(accountRepository.getByUniqueId("2")).thenReturn(new Account("2", "PLN", emptyList()));
        onNextStagingTransfer.call(new Unique<>("a", transfer().staging()));
        verify(accountRepository).save(new Unique<>("2", new Account("2", "PLN", List.of(transfer().completed()))));
    }

    @Test
    void persistCompletedTransaction() {
        when(accountRepository.getByUniqueId("1")).thenReturn(new Account("1", "PLN", emptyList()));
        when(accountRepository.getByUniqueId("2")).thenReturn(new Account("2", "PLN", emptyList()));
        onNextStagingTransfer.call(new Unique<>("a", transfer().staging()));
        verify(transferRepository).save(new Unique<>("a", transfer().completed()));
    }

    private Transfer transfer() {
        return new Transfer("1", "2", BigDecimal.ONE, "PLN");

    }
}