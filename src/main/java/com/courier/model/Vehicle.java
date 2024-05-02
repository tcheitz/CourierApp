package com.courier.model;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class Vehicle {
    public static int maximumSpeed;
    public static double maximumCarryingWeight;

    private List<Package> packages;
    private double returnTime;

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public double getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(double returnTime) {
        this.returnTime = returnTime;
    }

    public static Vehicle getNextVehicle(Vehicle[] vehicles) {
        return Arrays.stream(vehicles)
                .min(Comparator.comparing(Vehicle::getReturnTime))
                .orElseThrow(IllegalStateException::new);
    }
}
