package com.courier.service;

import com.courier.model.Package;
import com.courier.model.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class VehicleService {
    public static void shippingPackages(Package[] packages, int numberOfVehicles, int maximumSpeed, int maximumCarryingWeight, PackageService packageService) {
        List<Package> currentTripPackageList = new ArrayList<>(Arrays.asList(packages));
        Vehicle[] vehicles = new Vehicle[numberOfVehicles];
        double currentTime = 0;

        for (int i = 0; i < numberOfVehicles && currentTripPackageList.size() != 0; i++) {
            vehicles[i] = new Vehicle(maximumSpeed);
            packageService.loadPackages(vehicles[i], currentTripPackageList, maximumCarryingWeight, packages, currentTime);
        }
        while (currentTripPackageList.size() != 0) {

            Vehicle nextVehicle = Arrays.stream(vehicles).min(Comparator.comparing(v -> v.getReturnTime())).get();
            if (vehicles.length > 1) {
                for (Vehicle vehicle : vehicles) {
                    if (vehicle != nextVehicle) {
                        vehicle.setReturnTime(vehicle.getReturnTime() - nextVehicle.getReturnTime());

                    }
                }
                currentTime = currentTime + nextVehicle.getReturnTime();
            } else {
                currentTime = nextVehicle.getReturnTime();
            }
            packageService.loadPackages(nextVehicle, currentTripPackageList, maximumCarryingWeight, packages, currentTime);
        }
    }
}
