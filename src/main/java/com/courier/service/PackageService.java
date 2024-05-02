package com.courier.service;

import com.courier.model.Offer;
import com.courier.model.Package;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PackageService {
    public static double calculatePackageCost(Package aPackage, double baseDeliveryCost) {
        return baseDeliveryCost + (aPackage.getWeight() * 10) + (aPackage.getDistance() * 5);
    }

    private static double calculateDiscountedPrice(double basePrice, double discountPercentage) {
        return basePrice * (1 - discountPercentage / 100);
    }

    public static double applyOffer(Package aPackage, Offer offer, double baseDeliveryCost) {
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

        if (packages.size() == 1) {
            currentTrip = new ArrayList<>(packages);
            packages.removeAll(currentTrip);
            return currentTrip;
        }

        sortPackagesByWeightDescendingAndDistanceAscending(packages);

        for (int i = 0; i < packages.size(); i++) {
            currentTrip = collectPackagesIntoTrip(packages, maximumCarryingWeight, currentTrip, tempPackages, i);
            tempPackages.clear();
        }

        return currentTrip;
    }

    private List<Package> collectPackagesIntoTrip(List<Package> packages, double maximumCarryingWeight, List<Package> currentTrip, List<Package> tempPackages, int i) {
        double totalWeight = packages.get(i).getWeight();
        tempPackages.add(packages.get(i));
        double maxWeight = 0;
        for (int j = i + 1; j < packages.size(); j++) {
            Package nextPackage = packages.get(j);

            if (totalWeight + nextPackage.getWeight() <= maximumCarryingWeight) {
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

        return currentTrip;
    }

    private void sortPackagesByWeightDescendingAndDistanceAscending(List<Package> packages) {
        Collections.sort(packages, (currentPackage, Nextpackage) -> {
            if (Nextpackage.getWeight() == currentPackage.getWeight())
                return Double.compare(currentPackage.getDistance(), Nextpackage.getDistance());
            return Double.compare(Nextpackage.getWeight(), currentPackage.getWeight());
        });
    }

    public static Package readPackage(BufferedReader testCasesReader, List<Offer> offers, double baseDeliveryCost) throws IOException {
        String[] packageInfo = testCasesReader.readLine().split("\\s+");
        String pkgId = packageInfo[0];
        int weight = Integer.parseInt(packageInfo[1]);
        int distance = Integer.parseInt(packageInfo[2]);
        String offerCode = packageInfo.length >= 4 ? packageInfo[3] : "";
        double totalCost = baseDeliveryCost;
        double discount = 0;
        Package pkg;

        if (offerCode != "") {
            pkg = new Package(pkgId, weight, distance, offerCode);
            for (Offer offer : offers) {
                if (offer.getCode().equals(pkg.getOfferCode())) {
                    totalCost = applyOffer(pkg, offer, baseDeliveryCost);
                    discount = calculatePackageCost(pkg, baseDeliveryCost) - totalCost;
                    break;
                }
            }
        } else {
            pkg = new Package(pkgId, weight, distance);
        }

        if (!pkg.isOfferApplied())
            totalCost = calculatePackageCost(pkg, baseDeliveryCost);

        pkg.setDiscount(discount);
        pkg.setTotalCost(totalCost);

        return pkg;
    }
}
