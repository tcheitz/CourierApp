package com.courier.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class PackageModelTest {
    Package aPackage;

    @Test
    @DisplayName("Negative values for Package Constructor")
    void shouldThrowExceptionForConstructorNegativeArguments(){
        Throwable exception = assertThrows(IllegalArgumentException.class, () -> {
            aPackage = new Package("PKG1",40,-10,"OFR001");
        });
        assertEquals("Weight, distance values should be positive",exception.getMessage());

        Throwable exception1 = assertThrows(IllegalArgumentException.class, () -> {
            aPackage = new Package("PKG1",40,-10);
        });
        assertEquals("Weight, distance values should be positive",exception.getMessage());
    }
}
