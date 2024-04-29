package com.courier.model;

public class Package {
    private String id;
    private int weight;
    private int distance;
    private boolean offerApplied;
    private String offerCode;
    private double discount;
    private double totalCost;
    private Vehicle vehicle;

    public Package(String id, int weight, int distance) {
        if(weight<0 || distance<0) throw new IllegalArgumentException("Weight, distance values should be positive");
        this.id = id;
        this.weight = weight;
        this.distance = distance;
    }


    public Package(String id, int weight, int distance, String offerCode) {
        if(weight<0 || distance<0) throw new IllegalArgumentException("Weight, distance values should be positive");
        this.id = id;
        this.weight = weight;
        this.distance = distance;
        this.offerCode = offerCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
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
}