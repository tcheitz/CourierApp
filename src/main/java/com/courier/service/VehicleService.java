package com.courier.service;

import com.courier.model.Package;
import com.courier.model.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

public class VehicleService {
    private static PackageService packageService = new PackageService();
    static double currentTime;

    public void shipPackages(Package[] packages, int numberOfVehicles) {
        List<Package> currentTripPackageList = new ArrayList<>(Arrays.asList(packages));
        Vehicle[] vehicles = new Vehicle[numberOfVehicles];
        currentTime = 0;
        for (int i = 0; i < numberOfVehicles && !currentTripPackageList.isEmpty(); i++) {
            vehicles[i] =new Vehicle();
            packageService.loadPackages(vehicles[i], currentTripPackageList, packages);
        }

        while (!currentTripPackageList.isEmpty()) {
            Vehicle nextVehicle = Vehicle.getNextVehicle(vehicles);
            updateCurrentTime(nextVehicle, vehicles);
            packageService.loadPackages(nextVehicle, currentTripPackageList, packages);
        }
    }

    private void updateCurrentTime( Vehicle nextVehicle, Vehicle[] vehicles) {
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
    }
}
