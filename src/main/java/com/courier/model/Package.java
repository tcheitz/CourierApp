package com.courier.model;

public class Package {
    private String id;
    private double weight;
    private double distance;
    private boolean offerApplied;
    private String offerCode;
    private double discount;
    private double totalCost;
    private Vehicle vehicle;
    double estimatedDeliveryTime;


    public Package(String id, double weight, double distance) {
        validatePackage(weight, distance);
        this.id = id;
        this.weight = weight;
        this.distance = distance;
    }


    public Package(String id, double weight, double distance, String offerCode) {
        validatePackage(weight, distance);
        this.id = id;
        this.weight = weight;
        this.distance = distance;
        this.offerCode = offerCode;
    }

    private static void validatePackage(double weight, double distance) {
        if (weight <= 0 || distance <= 0)
            throw new IllegalArgumentException("Weight, distance values should be more than zero");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public boolean isOfferApplied() {
        return offerApplied;
    }

    public void setOfferApplied(boolean offerApplied) {
        this.offerApplied = offerApplied;
    }

    public String getOfferCode() {
        return offerCode;
    }

    public void setOfferCode(String offerCode) {
        this.offerCode = offerCode;
    }

    public double getDiscount() {
        return discount;
    }

    public void setDiscount(double discount) {
        this.discount = discount;
    }

    public double getTotalCost() {
        return totalCost;
    }

    public void setTotalCost(double totalCost) {
        this.totalCost = totalCost;
    }

    public Vehicle getVehicle() {
        return vehicle;
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
}
