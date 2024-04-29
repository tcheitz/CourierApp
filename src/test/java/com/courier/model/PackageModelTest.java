package com.courier.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PackageModelTest {
    Package aPackage;

    @Test
    @DisplayName("Negative values for Package Constructor")
    void shouldThrowExceptionForConstructorNegativeArguments(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            aPackage = new Package("PKG1",40,-10,"OFR001");
        });
        assertEquals("Weight, distance values should be more than zero",exception.getMessage());

        Throwable exception1 = assertThrows(IllegalArgumentException.class, () -> {
            aPackage = new Package("PKG1",40,-10);
        });
        assertEquals("Weight, distance values should be more than zero",exception.getMessage());
    }
    @Test
    void assertThrowsIllegalArgumentException() {
        // Negative Values
        assertThrows(IllegalArgumentException.class, () -> {
            aPackage =new Package("PKG67832",-2,0,"OFR8923");
        });

        // Zero Values
        assertThrows(IllegalArgumentException.class, () -> {
            aPackage =new Package("PKG67832",0,0,"OFR8923");
        });
    }

    @Test
    void shouldThrowExceptionForZeroValues(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            aPackage = new Package("PKG1",0,0.5,"OFR001");
        });
        assertEquals("Weight, distance values should be more than zero",exception.getMessage());
    }
    @Test
    void shouldNotThrowExceptionForZeroDecimals(){
        assertDoesNotThrow(()->new Package("PKG62",0.8,0.56,"OFR022"));
    }
    @Test
    void intiallyOfferShouldNotBeApplied(){
        aPackage = new Package("PKG378",78,67,"OFR003");
        assertFalse(aPackage.isOfferApplied());
    }
}
