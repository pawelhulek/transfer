package com.hulek.money.transfer.dto;

import java.math.BigDecimal;
import java.util.Objects;

public final class Transfer {
    private final String from;
    private final String to;
    private final BigDecimal amount;
    private final String currency;
    private final TransactionStatus transactionStatus;

    public Transfer(String from, String to, BigDecimal amount, String currency) {
        this(from, to, amount, currency, null);
    }

    private Transfer(String from, String to, BigDecimal amount, String currency, TransactionStatus transactionStatus) {
        this.from = from;
        this.to = to;
        this.amount = amount;
        this.currency = currency;
        this.transactionStatus = transactionStatus;
    }

    public Transfer staging() {
        return new Transfer(from, to, amount, currency, TransactionStatus.STAGING);
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

    public TransactionStatus getTransactionStatus() {
        return transactionStatus;
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
                        Objects.equals(transactionStatus, transfer.transactionStatus) &&
                        Objects.equals(currency, transfer.currency);
    }

    @Override
    public String toString() {
        return "Transfer{" +
                "from='" + from + '\'' +
                ", to='" + to + '\'' +
                ", amount=" + amount +
                ", currency='" + currency + '\'' +
                ", transactionStatus=" + transactionStatus +
                '}';
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to, amount, transactionStatus, currency);
    }
}
