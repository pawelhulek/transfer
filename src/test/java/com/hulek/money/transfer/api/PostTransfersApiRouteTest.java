package com.hulek.money.transfer.api;

import com.google.gson.Gson;
import com.hulek.money.transfer.dto.Transfer;
import com.hulek.money.transfer.dto.Unique;
import com.hulek.money.transfer.repository.TransfersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import spark.Request;
import spark.Response;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PostTransfersApiRouteTest {
    public static final String SOME_TRANSFER_JSON_REQUEST = "{\"from\":\"2ds\",\"to\":\"avs\",\"amount\":2.21,\"currency\":\"USD\",\"transactionStatus\":\"STAGING\"}";
    private PostRoute postRoute;
    private Request requestMock;
    private Response responseMock;
    private Gson gson;
    ArgumentCaptor<Unique> captor = ArgumentCaptor.forClass(Unique.class);
    ArgumentCaptor<String> headerCaptor = ArgumentCaptor.forClass(String.class);
    private TransfersRepository repository;

    @BeforeEach
    void beforeEach() {
        requestMock = mock(Request.class);
        responseMock = mock(Response.class);
        gson = new Gson();
        repository = mock(TransfersRepository.class);
        postRoute = new PostRoute(gson, repository, Transfer.class, "transfers");

    }

    @Test
    void transferInResponseBodyIsObligatory() {
        when(requestMock.body()).thenReturn("");
        assertThrows(IllegalArgumentException.class, () -> postRoute.handle(requestMock, responseMock));
    }

    @Test
    void transferInTheBodyIsAccepted() {
        when(requestMock.body()).thenReturn(SOME_TRANSFER_JSON_REQUEST);
        assertDoesNotThrow(() -> postRoute.handle(requestMock, responseMock));
    }

    @Test
    void persistTransfer() {
        when(requestMock.body()).thenReturn(SOME_TRANSFER_JSON_REQUEST);
        postRoute.handle(requestMock, responseMock);
        verify(repository).save(captor.capture());
        assertEquals(new Transfer("2ds", "avs", new BigDecimal("2.21"), "USD"), captor.getValue().getValue());
    }

    @Test
    void persistedIdIsTheSameAsTheOneInHeader() {
        when(requestMock.body()).thenReturn(SOME_TRANSFER_JSON_REQUEST);
        postRoute.handle(requestMock, responseMock);
        verify(repository).save(captor.capture());
        verify(responseMock).header(any(), headerCaptor.capture());

        assertEquals("/transfers/" + captor.getValue().getId(), headerCaptor.getValue());

    }
}