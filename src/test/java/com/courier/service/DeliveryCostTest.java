package com.courier.service;

import com.courier.model.Offer;
import com.courier.model.Package;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeliveryCostTest {

    Package aPackage;

    double baseDeliveryCost = 100;


    @ParameterizedTest
    @CsvSource("0.1,0.1")
    void packageCostShouldBeMoreThanBaseCost(double weight, double distance) {
        aPackage = new Package("PKG42", weight, distance, "OFR001");
        double cost = aPackage.calculatePackageCost(baseDeliveryCost);
        assertTrue(cost > baseDeliveryCost);
    }

    @Test
    void whenDiscountGivenTestPackageCost() {
        Offer offer = new Offer("OFR001", 10, 250, 50, 200, 7);
        aPackage = new Package("PKG723", 100, 100, "OFR001");
        double actualCost =(baseDeliveryCost+(aPackage.getWeight() * 10) + (aPackage.getDistance() * 5));
        double expectedDiscount = actualCost*(1-  7.0 /100);
        double calculatedDiscount = aPackage.calculateDiscountedCost(actualCost,offer.getDiscountPercentage());
        assertTrue(actualCost > calculatedDiscount);
        assertEquals(expectedDiscount,calculatedDiscount);
    }


}
