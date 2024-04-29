package com.courier.service;

import com.courier.model.Offer;
import com.courier.model.Package;

public class PackageService {
    public double calculatePackageCost(Package aPackage, double baseDeliveryCost) {
        return baseDeliveryCost+(aPackage.getWeight()*10)+(aPackage.getDistance());
    }
    private static double calculateDiscountedPrice(double basePrice, double discountPercentage) {
        return basePrice * (1 - discountPercentage / 100);
    }
    public double applyOffer(Package aPackage, Offer offer, double baseDeliveryCost) {
        double packageCost = calculatePackageCost(aPackage, baseDeliveryCost);
        if (offer.getMinimumWeight() <= aPackage.getWeight() && offer.getMaximumWeight() >= aPackage.getWeight() &&
                offer.getMinimumDistance() <= aPackage.getDistance() && offer.getMaximumDistance() >= aPackage.getDistance()) {
            aPackage.setOfferApplied(true);
            return calculateDiscountedPrice(packageCost, offer.getDiscountPercentage());
        }
        return packageCost;
    }
}
