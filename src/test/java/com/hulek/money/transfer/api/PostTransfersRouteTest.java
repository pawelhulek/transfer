package com.hulek.money.transfer.api;

import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import spark.Request;
import spark.Response;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class PostTransfersRouteTest {
    private PostTransfersRoute postTransfersRoute;
    private Request requestMock;
    private Response responseMock;
    private Gson gson;

    @BeforeEach
    public void beforeEach() {
        requestMock = mock(Request.class);
        responseMock = mock(Response.class);
        gson = new Gson();
        postTransfersRoute = new PostTransfersRoute(gson);

    }

    @Test
    void transferInResponseBodyIsObligatory() {
        when(requestMock.body()).thenReturn("");
        assertThrows(IllegalArgumentException.class, () -> postTransfersRoute.handle(requestMock, responseMock));
    }

    @Test
    void transferInTheBodyIsAccepted() {
        when(requestMock.body()).thenReturn("{\"from\":\"2ds\",\"to\":\"avs\",\"amount\":2.21,\"currency\":\"USD\"}");
        assertDoesNotThrow(() -> postTransfersRoute.handle(requestMock, responseMock));

    }

}