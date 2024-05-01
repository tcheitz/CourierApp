package com.courier.service;

import com.courier.model.Offer;
import com.courier.model.Package;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.courier.service.PackageService.applyOffer;
import static com.courier.service.PackageService.calculatePackageCost;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeliveryCostTest {

    Package aPackage;

    double baseDeliveryCost = 100;


    @ParameterizedTest
    @CsvSource("0.1,0.1")
    void packageCostShouldBeMoreThanBaseCost(double weight, double distance) {
        aPackage = new Package("PKG42", weight, distance, "OFR001");
        double cost = calculatePackageCost(aPackage, baseDeliveryCost);
        assertTrue(cost > baseDeliveryCost);
    }

    @Test
    void whenDiscountGivenPackageCostShouldBeLessThanTotalCost() {
        aPackage = new Package("PKG723", 100, 100, "OFR001");
        Offer offer = new Offer("OFR001", 10, 250, 50, 200, 7);
        double actualCost = calculatePackageCost(aPackage, baseDeliveryCost);
        double offeredCost = applyOffer(aPackage, offer, baseDeliveryCost);
        assertTrue(actualCost > offeredCost);
        assertTrue(aPackage.isOfferApplied());
    }


}
