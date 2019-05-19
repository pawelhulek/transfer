package com.hulek.money.transfer.dto;

import nl.jqno.equalsverifier.EqualsVerifier;
import org.junit.jupiter.api.Test;

class AccountTest {

    @Test
    void verifyEquals() {
        EqualsVerifier.forClass(Account.class).verify();
    }
}