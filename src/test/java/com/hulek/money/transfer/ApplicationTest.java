package com.hulek.money.transfer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;
import static java.net.http.HttpRequest.BodyPublishers;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationTest {
    @BeforeEach
    void startup() {
        Application.main();
        Spark.awaitInitialization();
    }

    @AfterEach
    void stop() {
        Spark.stop();
        Spark.awaitStop();
    }

    @Test
    void postNewTransfer() throws IOException, InterruptedException {

        HttpResponse<Stream<String>> respone = postTransfer();
        assertEquals(SC_CREATED, respone.statusCode());
        assertTrue(respone.headers().firstValue("Location").isPresent());

    }

    private HttpResponse<Stream<String>> postTransfer() throws IOException, InterruptedException {
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = newBuilder(URI.create("http://localhost:4567/transfers"))
                .POST(BodyPublishers.ofString("{\"from\":\"1\",\"to\":\"2\",\"amount\":1.01,\"currency\":\"PLN\"}"))
                .build();
        return httpClient.send(httpRequest, BodyHandlers.ofLines());
    }

    @Test
    void getTransfer() throws IOException, InterruptedException {
        var response = postTransfer();
        var locationURI = response.headers().firstValue("Location").get();
        String json = getData("http://localhost:4567" + locationURI);
        assertEquals("{\"from\":\"1\",\"to\":\"2\",\"amount\":1.01,\"currency\":\"PLN\"}", json);
    }

    @Test
    void getAccountDetails() throws IOException, InterruptedException {

        String json = getData("http://localhost:4567/accounts/1");
        var expectedJson = "{\"number\":\"1\",\"currency\":\"PLN\",\"transfers\":[]}";
        assertEquals(expectedJson, json);
    }

    private String getData(String s) throws IOException, InterruptedException {
        var httpRequest = newBuilder(URI.create(s))
                .GET()
                .build();
        HttpResponse<Stream<String>> getResponse = HttpClient.newHttpClient().send(httpRequest, BodyHandlers.ofLines());
        return getResponse.body().reduce((l, r) -> l + r).get();
    }

    @Test
    void executeTransferOnAccounts() throws IOException, InterruptedException {

        postTransfer();
        sleep(Duration.ofSeconds(2).toMillis());
        String json = getData("http://localhost:4567/accounts/1");
        var expectedJson = "{\"number\":\"1\",\"currency\":\"PLN\",\"transfers\":[{\"from\":\"1\",\"to\":\"2\",\"amount\":1.01,\"currency\":\"PLN\",\"transactionStatus\":\"STAGING\"}]}";
        assertEquals(expectedJson, json);

    }
}