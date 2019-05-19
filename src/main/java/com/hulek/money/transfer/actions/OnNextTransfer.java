package com.hulek.money.transfer.actions;

import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import rx.functions.Action1;

public class OnNextTransfer implements Action1<Unique<Transfer>> {
    @Override
    public void call(Unique<Transfer> transferUnique) {

    }
}
