package com.hulek.money.transfer.dto;

import java.math.BigDecimal;
import java.util.Objects;

public final class Transfer {
    private final String from;
    private final String to;
    private final BigDecimal amount;
    private final String currency;

    public Transfer(String from, String to, BigDecimal amount, String currency) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.currency = currency;
    }

    public String getFrom() {
        return from;
    }

    public String getTo() {
        return to;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getCurrency() {
        return currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transfer transfer = (Transfer) o;
        return
                Objects.equals(from, transfer.from) &&
                        Objects.equals(to, transfer.to) &&
                        Objects.equals(amount, transfer.amount) &&
                        Objects.equals(currency, transfer.currency);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, amount, currency);
    }
}
