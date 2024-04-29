package com.courier.service;

import com.courier.model.Package;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.courier.service.PackageService.calculatePackageCost;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PackageServiceTest {
    @ParameterizedTest
    @CsvSource("0.1,0.1")
    void packageCostShouldBeMoreThanBaseCost(double weight,double distance){
        Package aPackage = new Package("PKG42",weight,distance,"OFR001");
        double baseDeliveryCost = 0;
        double cost = calculatePackageCost(aPackage,baseDeliveryCost);
        assertTrue(cost>baseDeliveryCost);
    }


}
