package com.hulek.money.transfer.service;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Month;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@Tag("integration")
class CurrencyExchangeServiceTest {
    @Test
    void getExampleRate() throws IOException, InterruptedException {
        var exchange = new CurrencyExchangeService();
        var rate = exchange.getRate(LocalDate.of(2019, Month.JANUARY, 1), "PLN", "USD");
        assertEquals(new BigDecimal("0.265268597"), rate);
    }

    @Test
    void cantGetRateFromFuture() throws IOException, InterruptedException {
        var exchange = new CurrencyExchangeService();
        assertThrows(Exception.class, () -> exchange.getRate(LocalDate.now().plusDays(2), "PLN", "USD"));
    }

    @Test
    void cantGetRateFromInvalidCurrencies() throws IOException, InterruptedException {
        var exchange = new CurrencyExchangeService();
        assertThrows(Exception.class, exchange.getRate(LocalDate.of(2019, Month.JANUARY, 1), "ASD", "USD");
    }
}