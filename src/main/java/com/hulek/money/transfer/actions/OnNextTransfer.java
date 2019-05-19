package com.hulek.money.transfer.actions;

import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.TransfersRepository;
import rx.functions.Action1;

public class OnNextTransfer implements Action1<Unique<Transfer>> {
    private final TransfersRepository repository;

    public OnNextTransfer(TransfersRepository repository) {
        this.repository = repository;
    }

    @Override
    public void call(Unique<Transfer> transferUnique) {
        var transfer = transferUnique.getValue().complete();
        var completedTransfer = new Unique<>(transferUnique.getId(), transfer);
        repository.save(completedTransfer);

    }
}
