package com.courier.model;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Package {
    Offer offer;
    private String id;
    private double weight;
    private double distance;
    private boolean offerApplied;
    private String offerCode;
    private double discount;
    private double totalCost;
    private Vehicle vehicle;
    private double estimatedDeliveryTime;
    private double baseCost;


    public Package(String id, double weight, double distance, String offerCode) {
        validatePackage(weight, distance);
        this.id = id;
        this.weight = weight;
        this.distance = distance;
        this.offerCode = offerCode;
    }

    public Package(String id, double weight, double distance, String offerCode, double baseCost) {
        validatePackage(weight, distance);
        this.id = id;
        this.weight = weight;
        this.distance = distance;
        this.offerCode = offerCode;
        this.baseCost = baseCost;
        this.totalCost = calculatePackageCost(baseCost);
        if (isOfferValid(offerCode)) {
            applyOffer();
        }
    }

    private static void validatePackage(double weight, double distance) {
        if (weight <= 0 || distance <= 0)
            throw new IllegalArgumentException("Weight, distance values should be more than zero");
    }

    public String getId() {
        return id;
    }

    public double getWeight() {
        return weight;
    }

    public double getDistance() {
        return distance;
    }

    public boolean isOfferApplied() {
        return offerApplied;
    }

    public void setOfferApplied(boolean offerApplied) {
        this.offerApplied = offerApplied;
    }


    public double getDiscount() {
        return discount;
    }


    public double getTotalCost() {
        return totalCost;
    }


    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    public double getEstimatedDeliveryTime() {
        return estimatedDeliveryTime;
    }

    public void setEstimatedDeliveryTime(double estimatedDeliveryTime) {
        this.estimatedDeliveryTime = estimatedDeliveryTime;
    }

    public double calculatePackageCost(double baseCost) {
        return baseCost + (this.getWeight() * 10) + (this.getDistance() * 5);
    }

    public double calculateDiscountedCost(double totalCost, double discountPercentage) {
        return totalCost * (1 - discountPercentage / 100);
    }

    public boolean isOfferValid(String code) {
        offer = Offer.getOffer(code);
        if (offer != null) {
            return (offer.getMinimumWeight() <= this.getWeight() && offer.getMaximumWeight() >= this.getWeight() &&
                    offer.getMinimumDistance() <= this.getDistance() && offer.getMaximumDistance() >= this.getDistance());
        }
        return false;
    }

    public void applyOffer() {
        double packageCost = calculatePackageCost(this.baseCost);
        setOfferApplied(true);
        totalCost = (calculateDiscountedCost(packageCost, offer.getDiscountPercentage()));
        discount = (packageCost - this.getTotalCost());
    }

    public static Package findLongestDelivery(List<Package> packageList) {
        return packageList.stream()
                .max(Comparator.comparing(Package::getDistance))
                .orElseThrow(IllegalArgumentException::new);
    }
    public static void sortPackages(List<Package> packages) {
        Collections.sort(packages, (currentPackage, Nextpackage) -> {
            if (Nextpackage.getWeight() == currentPackage.getWeight())
                return Double.compare(currentPackage.getDistance(), Nextpackage.getDistance());
            return Double.compare(Nextpackage.getWeight(), currentPackage.getWeight());
        });
    }
}

