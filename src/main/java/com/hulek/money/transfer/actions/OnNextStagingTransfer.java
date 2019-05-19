package com.hulek.money.transfer.actions;

import com.hulek.money.transfer.dto.Account;
import com.hulek.money.transfer.dto.TransactionStatus;
import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.Repository;
import rx.functions.Action1;

public class OnNextStagingTransfer implements Action1<Unique<Transfer>> {
    private final Repository<Account> accountRepository;
    private final Repository<Transfer> transferRepository;

    public OnNextStagingTransfer(Repository<Account> accountRepository, Repository<Transfer> transferRepository) {
        this.accountRepository = accountRepository;
        this.transferRepository = transferRepository;
    }

    @Override
    public void call(Unique<Transfer> transferUnique) {
        if (transferUnique.getValue().getTransactionStatus() == TransactionStatus.STAGING) {
            Transfer completed = transferUnique.getValue().completed();
            Account fromAccount = accountRepository.getByUniqueId(transferUnique.getValue().getFrom());
            accountRepository.save(new Unique<>(fromAccount.getNumber(), fromAccount.withTransfer(completed)));
            Account toccount = accountRepository.getByUniqueId(transferUnique.getValue().getTo());
            accountRepository.save(new Unique<>(toccount.getNumber(), toccount.withTransfer(completed)));
            transferRepository.save(new Unique<>(transferUnique.getId(), completed));
        }
    }
}
