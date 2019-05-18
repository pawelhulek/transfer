package com.hulek.money.transfer.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class TransferTest {
    @Test
    public void equalsVerifier() {
        EqualsVerifier.forClass(Transfer.class).verify();
    }

}