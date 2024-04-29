package com.courier.service;

import com.courier.model.Offer;
import com.courier.model.Package;
import com.courier.model.Vehicle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class PackageService {
    public double calculatePackageCost(Package aPackage, double baseDeliveryCost) {
        return baseDeliveryCost + (aPackage.getWeight() * 10) + (aPackage.getDistance()*5);
    }

    private static double calculateDiscountedPrice(double basePrice, double discountPercentage) {
        return basePrice * (1 - discountPercentage / 100);
    }

    public double applyOffer(Package aPackage, Offer offer, double baseDeliveryCost) {
        double packageCost = calculatePackageCost(aPackage, baseDeliveryCost);
        if (offer.getMinimumWeight() <= aPackage.getWeight() && offer.getMaximumWeight() >= aPackage.getWeight() &&
                offer.getMinimumDistance() <= aPackage.getDistance() && offer.getMaximumDistance() >= aPackage.getDistance()) {
            aPackage.setOfferApplied(true);
            return calculateDiscountedPrice(packageCost, offer.getDiscountPercentage());
        }
        return packageCost;
    }

    public List<Package> filterPackages(List<Package> packages, double maximumCarryingWeight) {

        List<Package> currentTrip = new ArrayList<>();
        List<Package> tempPackages = new ArrayList<>();
        double totalWeight = 0;
        double maxWeight = 0;

        if (packages.size() == 1) {
            currentTrip = new ArrayList<>(packages);
            packages.removeAll(currentTrip);
            return currentTrip;
        }
        // Sort the list in descending order
        Collections.sort(packages, (currentPackage, Nextpackage) -> {
            if (Nextpackage.getWeight() == currentPackage.getWeight())
                return Double.compare(currentPackage.getDistance(), Nextpackage.getDistance());
            return Double.compare(Nextpackage.getWeight(), currentPackage.getWeight());
        });

        for (int i = 0; i < packages.size(); i++) {
            totalWeight = packages.get(i).getWeight();
            tempPackages.add(packages.get(i));

            for (int j = i + 1; j < packages.size(); j++) {
                if (totalWeight + packages.get(j).getWeight() <= maximumCarryingWeight) {
                    totalWeight += packages.get(j).getWeight();
                    tempPackages.add(packages.get(j));
                } else if (totalWeight > maxWeight) {
                    maxWeight = totalWeight;
                } else if (totalWeight < packages.get(j).getWeight() && tempPackages.size() == 0) {
                    tempPackages.add(packages.get(j));
                }
            }

            if (tempPackages.size() > currentTrip.size()) currentTrip = new ArrayList<>(tempPackages);

            tempPackages.clear();
        }

        return currentTrip;
    }
    public void loadPackages(Vehicle vehicle, List<Package> newPack, int maxCarriableWeight, Package[] packages, double currentTime) {
        List<Package> packageList = filterPackages(newPack, maxCarriableWeight);
        vehicle.setPackages(packageList);

        Package longestDelivery = packageList.stream().max(Comparator.comparing(pkg -> pkg.getDistance())).get();


        for (Package pkg : packageList) {
            newPack.removeIf(p -> p.getId() == pkg.getId());
            for (Package orgPkg : packages) {
                if (pkg.getId() == orgPkg.getId()) {
                    orgPkg.setVehicle(vehicle);
                    orgPkg.setEstimatedDeliveryTime(currentTime + orgPkg.getDistance() / vehicle.getMaximumSpeed());
                    break;
                }
            }
        }
        vehicle.setReturnTime(currentTime + vehicle.getReturnTime() + 2 * ( longestDelivery.getDistance() / vehicle.getMaximumSpeed()));
    }
}
