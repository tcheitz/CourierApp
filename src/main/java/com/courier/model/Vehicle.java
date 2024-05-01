package com.courier.model;

import java.util.ArrayList;
import java.util.List;

public class Vehicle {
    private int maximumSpeed;
    private List<Package> packages;
    private double returnTime;

    public Vehicle(int maximumSpeed) {
        if(maximumSpeed<0) throw new IllegalArgumentException("Speed should be positive");
        this.maximumSpeed = maximumSpeed;
        this.packages = new ArrayList<>();
    }

    public int getMaximumSpeed() {
        return maximumSpeed;
    }

    public void setPackages(List<Package> packages) {
        this.packages = packages;
    }

    public double getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(double returnTime) {
        this.returnTime = returnTime;
    }
}
