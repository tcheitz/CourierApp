package com.courier.service;

import com.courier.model.Package;
import com.courier.model.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class VehicleService {
    private static PackageService packageService = new PackageService();

    public static void shipPackages(Package[] packages, int numberOfVehicles, int maximumSpeed, int maximumCarryingWeight) {
        List<Package> currentTripPackageList = new ArrayList<>(Arrays.asList(packages));
        Vehicle[] vehicles = new Vehicle[numberOfVehicles];
        double currentTime = 0;

        for (int i = 0; i < numberOfVehicles && !currentTripPackageList.isEmpty(); i++) {
            vehicles[i] = new Vehicle(maximumSpeed);
            currentTime = loadPackagesIntoVehicle(vehicles[i], currentTripPackageList, maximumCarryingWeight, packages, currentTime);
        }

        while (!currentTripPackageList.isEmpty()) {
            Vehicle nextVehicle = Arrays.stream(vehicles)
                    .min(Comparator.comparing(Vehicle::getReturnTime))
                    .orElseThrow(IllegalStateException::new);

            currentTime = updateCurrentTime(currentTime, nextVehicle, vehicles);

            currentTime = loadPackagesIntoVehicle(nextVehicle, currentTripPackageList, maximumCarryingWeight, packages, currentTime);
        }
    }

    private static double loadPackagesIntoVehicle(Vehicle vehicle, List<Package> currentTripPackageList, int maximumCarryingWeight, Package[] packages, double currentTime) {
        List<Package> packageList = packageService.filterPackages(currentTripPackageList, maximumCarryingWeight);
        vehicle.setPackages(packageList);

        Package longestDelivery = findLongestDelivery(packageList);

        for (Package aPackage : packageList) {
            for (Package originalPackage : packages) {
                if (aPackage.getId() == originalPackage.getId()) {
                    originalPackage.setVehicle(vehicle);
                    originalPackage.setEstimatedDeliveryTime(currentTime + originalPackage.getDistance() / vehicle.getMaximumSpeed());
                    break;
                }
            }
            currentTripPackageList.removeIf(pkg -> pkg.getId() == aPackage.getId());
        }

        vehicle.setReturnTime(currentTime + 2 * (longestDelivery.getDistance() / vehicle.getMaximumSpeed()));
        return currentTime;
    }

    private static Package findLongestDelivery(List<Package> packageList) {
        return packageList.stream()
                .max(Comparator.comparing(Package::getDistance))
                .orElseThrow(IllegalArgumentException::new);
    }

    private static double updateCurrentTime(double currentTime, Vehicle nextVehicle, Vehicle[] vehicles) {
        if (vehicles.length > 1) {
            for (Vehicle vehicle : vehicles) {
                if (vehicle != nextVehicle) {
                    vehicle.setReturnTime(vehicle.getReturnTime() - nextVehicle.getReturnTime());
                }
            }
            currentTime += nextVehicle.getReturnTime();
        } else {
            currentTime = nextVehicle.getReturnTime();
        }
        return currentTime;
    }
}
