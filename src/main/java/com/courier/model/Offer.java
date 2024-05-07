package com.courier.model;

import java.util.List;

import static com.courier.service.OfferService.loadOffers;

public class Offer {
    private String code;
    private int minimumDistance;
    private int maximumDistance;
    private int minimumWeight;
    private int maximumWeight;
    private int discountPercentage;
    static List<Offer> offerList = loadOffers();
    public Offer(String code, int minimumDistance, int maximumDistance, int minimumWeight, int maximumWeight, int discountPercentage) {
        this.code = code;
        if (minimumDistance < 0 || maximumDistance < 0 || minimumWeight < 0 || maximumWeight < 0 || discountPercentage < 0)
            throw new IllegalArgumentException("Distance, weight and discount values should be positive");
        this.minimumDistance = minimumDistance;
        this.maximumDistance = maximumDistance;
        this.minimumWeight = minimumWeight;
        this.maximumWeight = maximumWeight;
        this.discountPercentage = discountPercentage;
    }

    public static Offer getOffer(String code) {
        for (Offer offer : offerList) {
            if (offer.getCode().equals(code)) {
                return offer;
            }
        }
        return null;
    }

    public String getCode() {
        return code;
    }

    public int getMinimumDistance() {
        return minimumDistance;
    }

    public int getMaximumDistance() {
        return maximumDistance;
    }

    public int getMinimumWeight() {
        return minimumWeight;
    }

    public int getMaximumWeight() {
        return maximumWeight;
    }

    public int getDiscountPercentage() {
        return discountPercentage;
    }


}
