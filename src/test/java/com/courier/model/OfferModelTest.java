package com.courier.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class OfferModelTest {
    Offer offer;
    @DisplayName("Negative values for Offer Constructor")
    @Test
    void shouldThrowExceptionForConstructorNegativeArguments(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            offer = new Offer("OFR001",20,80,10,40,-10);
        });
        assertEquals("Distance, weight and discount values should be positive",exception.getMessage());
    }

}
