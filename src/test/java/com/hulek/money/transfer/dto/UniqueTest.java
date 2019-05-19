package com.hulek.money.transfer.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class UniqueTest {

    @Test
    void equalsverify() {
        EqualsVerifier.forClass(Unique.class).verify();
    }
}