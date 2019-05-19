package com.hulek.money.transfer.actions;

import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.Repository;
import rx.functions.Action1;

import java.util.concurrent.TimeUnit;


public class OnNextTransfer implements Action1<Unique<Transfer>> {
    private final Repository<Transfer> repository;

    public OnNextTransfer(Repository<Transfer> repository) {
        this.repository = repository;
    }

    @Override
    public void call(Unique<Transfer> transferUnique) {
        if (transferUnique.getValue().getTransactionStatus() == null) {
            //normalize currency
            someBlockingOperation();
            var transfer = transferUnique.getValue().staging();
            var completedTransfer = new Unique<>(transferUnique.getId(), transfer);
            repository.save(completedTransfer);
        }
    }

    private void someBlockingOperation() {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
