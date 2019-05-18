package com.hulek.money.transfer;

import org.junit.jupiter.api.*;
import spark.Spark;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.stream.Stream;

import static java.net.http.HttpRequest.*;
import static javax.servlet.http.HttpServletResponse.*;
import static org.junit.jupiter.api.Assertions.*;

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
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest httpRequest = newBuilder(URI.create("http://localhost:4567/transfers")).POST(BodyPublishers.ofString("")).build();
        HttpResponse<Stream<String>> respone = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofLines());
        assertEquals(SC_CREATED, respone.statusCode());
        assertTrue(respone.headers().firstValue("Location").isPresent());
    }

}