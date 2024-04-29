package com.courier.service;

import com.courier.model.Package;
import com.courier.model.Vehicle;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilteringPackageTests {

    PackageService packageService = new PackageService();
    static double maximumCarryingWeight;

    @Test
    void shouldFilterMorePackagesThenMoreWeights() {
        Package[] packages = new Package[5];
        packages[0] = (new Package("PKG1", 50, 30, "OFR001"));
        packages[1] = ((new Package("PKG2", 75, 125, "OFR008")));
        packages[2] = (new Package("PKG3", 175, 100, "OFR003"));
        packages[3] = (new Package("PKG4", 110, 60, "OFR002"));
        packages[4] = (new Package("PKG5", 155, 95, "NA"));
        maximumCarryingWeight = 200;
        String[] packageIds = {"PKG4", "PKG2", "PKG3", "PKG5", "PKG1"};
        List<Package> packageList = new ArrayList<>(Arrays.asList(packages));
        List<String> filteredPackageIds = new ArrayList<>();
        while (!packageList.isEmpty()) {
            List<Package> shippingPackages = packageService.filterPackages(packageList, maximumCarryingWeight);
            for (Package aPackage : shippingPackages) {
                filteredPackageIds.add(aPackage.getId());
                packageList.removeIf(p -> p.getId() == aPackage.getId());
            }
        }
        assertEquals(Arrays.asList(packageIds), filteredPackageIds);

    }

    @Test
    void testDeliveryEstimationTime() {
        Package[] packages = new Package[5];
        packages[0] = (new Package("PKG1", 50, 30, "OFR001"));
        packages[1] = ((new Package("PKG2", 75, 125, "OFR008")));
        packages[2] = (new Package("PKG3", 175, 100, "OFR003"));
        packages[3] = (new Package("PKG4", 110, 60, "OFR002"));
        packages[4] = (new Package("PKG5", 155, 95, "NA"));
        maximumCarryingWeight = 200;
        Double[] estimationTimings = {3.98, 1.78, 1.42, 0.85, 4.19};
        int numberOfVehicles = 2;
        int maxSpeed = 70;
        int maximumCarryingWeight = 200;
        List<Package> newPack = new ArrayList<>(Arrays.asList(packages));
        Vehicle[] vehicles = new Vehicle[numberOfVehicles];
        double currentTime = 0;

        for (int i = 0; i < numberOfVehicles && newPack.size() != 0; i++) {
            vehicles[i] = new Vehicle(maxSpeed);
            packageService.loadPackages(vehicles[i], newPack, maximumCarryingWeight, packages, currentTime);
        }
        while (newPack.size() != 0) {

            Vehicle nextVehicle = Arrays.stream(vehicles).min(Comparator.comparing(v -> v.getReturnTime())).get();
            for (Vehicle vehicle : vehicles) {
                if (vehicle != nextVehicle)
                    vehicle.setReturnTime(vehicle.getReturnTime() - nextVehicle.getReturnTime());

            }
            currentTime = currentTime + nextVehicle.getReturnTime();
            packageService.loadPackages(nextVehicle, newPack, maximumCarryingWeight, packages, currentTime);
        }
        for (int i = 0; i < packages.length; i++) {
            assertEquals(estimationTimings[i], packages[i].getEstimatedDeliveryTime(), 0.025);
        }
    }

    @Test
    void shouldFilterLeastDistanceIfEqualweights() {
        List<Package> packages = new ArrayList<>();
        packages.add(new Package("PKG1", 50, 50));
        packages.add(new Package("PKG2", 50, 120));
        packages.add(new Package("PKG3", 50, 100));
        maximumCarryingWeight = 130;
        String[] packageIds = {"PKG1", "PKG3", "PKG2"};
        List<String> filteredPackageIds = new ArrayList<>();
        while (!packages.isEmpty()) {
            List<Package> packageList = packageService.filterPackages(packages, maximumCarryingWeight);
            for (Package aPackage : packageList) {
                filteredPackageIds.add(aPackage.getId());
                packages.removeIf(p -> p.getId() == aPackage.getId());
            }
        }
        assertEquals(Arrays.asList(packageIds), filteredPackageIds);
    }


}
