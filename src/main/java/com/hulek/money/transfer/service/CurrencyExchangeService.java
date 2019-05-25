package com.hulek.money.transfer.service;

import com.google.gson.Gson;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpResponse;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Map;

import static java.net.http.HttpClient.newHttpClient;
import static java.net.http.HttpRequest.newBuilder;

public class CurrencyExchangeService {
    public BigDecimal getRate(LocalDate start, String currencyFrom, String currencyTo) throws IOException, InterruptedException {
        //TODO: async with publisher
        HttpResponse<String> response = newHttpClient()
                .send(newBuilder(uri(start, currencyFrom, currencyTo)).build(), HttpResponse.BodyHandlers.ofString());
        return new Gson().fromJson(response.body(), ExchangeRatesApi.class).getRate();
    }

    private URI uri(LocalDate start, String currencyFrom, String currencyTo) {
        return URI.create("https://api.exchangeratesapi.io/history?base=" + currencyFrom + "&start_at=" + start.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "&end_at=" + start.plusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "&symbols=" + currencyTo);
    }

    private static class ExchangeRatesApi {
        private Map<String, Map<String, String>> rates;

        private BigDecimal getRate() {
            return rates.values().stream().findFirst().map(r -> r.values().stream().findFirst().get()).map(BigDecimal::new).get();
        }
    }
}
