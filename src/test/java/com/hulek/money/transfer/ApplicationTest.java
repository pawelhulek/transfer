package com.hulek.money.transfer;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Spark;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;

import static java.net.http.HttpRequest.BodyPublishers;
import static java.net.http.HttpRequest.newBuilder;
import static java.net.http.HttpResponse.BodyHandlers;
import static javax.servlet.http.HttpServletResponse.SC_CREATED;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ApplicationTest {

    @BeforeEach
    public void startup() {
        Application.main();
    }

    @AfterEach
    public void stop() {
        Spark.stop();
    }

    @Test
    public void postNewTransfer() throws IOException, InterruptedException {
        var httpClient = HttpClient.newHttpClient();
        var httpRequest = newBuilder(URI.create("http://localhost:4567/transfers"))
                .POST(BodyPublishers.ofString("{\"from\":\"1\",\"to\":\"2\",\"amount\":1.01,\"currency\":\"PLN\"}"))
                .build();
        var respone = httpClient.send(httpRequest, BodyHandlers.ofLines());
        assertEquals(SC_CREATED, respone.statusCode());
        assertTrue(respone.headers().firstValue("Location").isPresent());
    }

}