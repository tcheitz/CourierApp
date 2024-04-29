package com.courier.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class VehicleModelTest {
    Vehicle vehicle;
    @Test
    void shouldThrowExceptionForNegativeConstructorArguments(){
        Throwable exception = assertThrows(IllegalArgumentException.class,()->{
            vehicle = new Vehicle(-50);
        });
        assertEquals("Speed should be positive",exception.getMessage());
    }
}
