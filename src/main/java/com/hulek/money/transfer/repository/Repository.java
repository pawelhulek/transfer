package com.hulek.money.transfer.repository;

import com.hulek.money.transfer.dto.Unique;

public interface Repository<T> {
    void save(Unique<T> object);

    T getByUniqueId(String id);
}
