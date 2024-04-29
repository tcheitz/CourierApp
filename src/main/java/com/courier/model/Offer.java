package com.courier.model;

public class Offer {
    private String code;
    private int minimumDistance;
    private int maximumDistance;
    private int minimumWeight;
    private int maximumWeight;
    private int discountPercentage;

    public Offer(String code, int minimumDistance, int maximumDistance, int minimumWeight, int maximumWeight, int discountPercentage) {
        this.code = code;
        if (minimumDistance < 0 || maximumDistance < 0 || minimumWeight < 0 || maximumWeight < 0 || discountPercentage<0)
            throw new IllegalArgumentException("Distance, weight and discount values should be positive");
        this.minimumDistance = minimumDistance;
        this.maximumDistance = maximumDistance;
        this.minimumWeight = minimumWeight;
        this.maximumWeight = maximumWeight;
        this.discountPercentage = discountPercentage;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public int getMinimumDistance() {
        return minimumDistance;
    }

    public void setMinimumDistance(int minimumDistance) {
        this.minimumDistance = minimumDistance;
    }

    public int getMaximumDistance() {
        return maximumDistance;
    }

    public void setMaximumDistance(int maximumDistance) {
        this.maximumDistance = maximumDistance;
    }

    public int getMinimumWeight() {
        return minimumWeight;
    }

    public void setMinimumWeight(int minimumWeight) {
        this.minimumWeight = minimumWeight;
    }

    public int getMaximumWeight() {
        return maximumWeight;
    }

    public void setMaximumWeight(int maximumWeight) {
        this.maximumWeight = maximumWeight;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }

    public void setDiscountPercentage(int discountPercentage) {
        this.discountPercentage = discountPercentage;
    }
}
