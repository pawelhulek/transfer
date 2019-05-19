package com.hulek.money.transfer.actions;

import com.hulek.money.transfer.dto.Account;
import com.hulek.money.transfer.dto.TransactionStatus;
import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.Repository;
import rx.functions.Action1;

public class OnNextCompletedTransfer implements Action1<Unique<Transfer>> {
    private final Repository<Account> accountRepository;

    public OnNextCompletedTransfer(Repository<Account> accountRepository) {
        this.accountRepository = accountRepository;
    }

    @Override
    public void call(Unique<Transfer> transferUnique) {
        if (transferUnique.getValue().getTransactionStatus() == TransactionStatus.COMPLETED) {
            Account fromAccount = accountRepository.getByUniqueId(transferUnique.getValue().getFrom());
            accountRepository.save(new Unique<>(fromAccount.getNumber(), fromAccount.withTransfer(transferUnique.getValue())));
            Account toccount = accountRepository.getByUniqueId(transferUnique.getValue().getTo());
            accountRepository.save(new Unique<>(toccount.getNumber(), toccount.withTransfer(transferUnique.getValue())));
        }
    }
}
