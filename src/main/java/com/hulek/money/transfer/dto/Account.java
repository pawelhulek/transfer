package com.hulek.money.transfer.dto;

import java.util.List;
import java.util.Objects;

public final class Account {
    private final String number;
    private final String currency;
    private final List<Transfer> transfers;

    public Account(String number, String currency, List<Transfer> transfers) {
        this.number = number;
        this.currency = currency;
        this.transfers = transfers;
    }

    public String getNumber() {
        return number;
    }

    public String getCurrency() {
        return currency;
    }


    public List<Transfer> getTransfers() {
        return transfers;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return Objects.equals(number, account.number) &&
                Objects.equals(currency, account.currency) &&
                Objects.equals(transfers, account.transfers);
    }

    @Override
    public int hashCode() {
        return Objects.hash(number, currency, transfers);
    }
}
