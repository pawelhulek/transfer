package com.hulek.money.transfer.repository;

import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;

public interface TransfersRepository {
    void save(Unique<Transfer> transfer);
}
