package com.courier.service;

import com.courier.model.Package;

public class PackageService {
    public static double calculatePackageCost(Package aPackage, double baseDeliveryCost) {
        return baseDeliveryCost+(aPackage.getWeight()*10)+(aPackage.getDistance());
    }
}
