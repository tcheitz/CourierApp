package com.courier.service;

import com.courier.model.Package;
import com.courier.model.Vehicle;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;



public class PackageService {


    public Package[] readPackages(String line, BufferedReader reader) throws IOException {

        String[] offerInfo = line.split("\\s+");
        double baseDeliveryCost = Double.parseDouble(offerInfo[0]);
        int numberOfPackages = Integer.parseInt(offerInfo[1]);
        Package[] packages = new Package[numberOfPackages];

        for (int i = 0; i < numberOfPackages; i++) {
            String[] packageInfo = reader.readLine().split("\\s+");
            String pkgId = packageInfo[0];
            int weight = Integer.parseInt(packageInfo[1]);
            int distance = Integer.parseInt(packageInfo[2]);
            String offerCode = packageInfo.length >= 4 ? packageInfo[3] : "NA";
            packages[i] = new Package(pkgId, weight, distance, offerCode, baseDeliveryCost);
        }
        return packages;
    }


    public List<Package> filterPackages(List<Package> packages) {

        List<Package> currentTrip = new ArrayList<>(packages);

        if (packages.size() == 1) {
            packages.removeAll(currentTrip);
            return currentTrip;
        }

        sortPackages(packages);
        currentTrip=tagPackages(packages);
        return currentTrip;
    }

    private List<Package> tagPackages(List<Package> packages) {
        List<Package> currentTrip =new ArrayList<>();
        List<Package> tempPackages = new ArrayList<>();
        for (int i = 0; i < packages.size(); i++) {
            double totalWeight = packages.get(i).getWeight();
            tempPackages.add(packages.get(i));
            double maxWeight = 0;
            for (int j = i + 1; j < packages.size(); j++) {
                Package nextPackage = packages.get(j);

                if (totalWeight + nextPackage.getWeight() <= Vehicle.maximumCarryingWeight) {
                    totalWeight += nextPackage.getWeight();
                    tempPackages.add(nextPackage);
                } else if (totalWeight > maxWeight) {
                    maxWeight = totalWeight;
                } else if (totalWeight < nextPackage.getWeight() && tempPackages.isEmpty()) {
                    tempPackages.add(nextPackage);
                }
            }

            if (tempPackages.size() > currentTrip.size())
                currentTrip = new ArrayList<>(tempPackages);
            tempPackages.clear();

        }
        return currentTrip;
    }

    private void sortPackages(List<Package> packages) {
        Collections.sort(packages, (currentPackage, Nextpackage) -> {
            if (Nextpackage.getWeight() == currentPackage.getWeight())
                return Double.compare(currentPackage.getDistance(), Nextpackage.getDistance());
            return Double.compare(Nextpackage.getWeight(), currentPackage.getWeight());
        });
    }

    public void loadPackages(Vehicle vehicle, List<Package> currentTripPackageList, Package[] packages) {
        List<Package> packageList = filterPackages(currentTripPackageList);
        vehicle.setPackages(packageList);

        Package longestDelivery = Package.findLongestDelivery(packageList);

        for (Package aPackage : packageList) {
            for (Package originalPackage : packages) {
                if (aPackage.getId() == originalPackage.getId()) {
                    originalPackage.setVehicle(vehicle);
                    originalPackage.setEstimatedDeliveryTime(VehicleService.currentTime + originalPackage.getDistance() / Vehicle.maximumSpeed);
                    break;
                }
            }
            currentTripPackageList.removeIf(pkg -> pkg.getId() == aPackage.getId());
        }

        vehicle.setReturnTime(VehicleService.currentTime + 2 * (longestDelivery.getDistance() / Vehicle.maximumSpeed));

    }
}
