package com.courier;

import com.courier.model.Offer;
import com.courier.model.Package;
import com.courier.service.PackageService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import static com.courier.service.OfferService.loadOffers;
import static com.courier.service.VehicleService.shippingPackages;


public class App {
    static PackageService packageService = new PackageService();

    public static void main(String[] args) {
        String fileName = args[0];
        InputStream inputStream = App.class.getResourceAsStream("/"+fileName);

        try (BufferedReader testCasesReader = new BufferedReader(new InputStreamReader(inputStream))) {
            List<Offer> offers = loadOffers();
            String line;

            while ((line = testCasesReader.readLine()) != null) {
                String[] offerInfo = line.split("\\s+");
                double baseDeliveryCost = Double.parseDouble(offerInfo[0]);
                int numberOfPackages = Integer.parseInt(offerInfo[1]);
                Package[] packages = new Package[numberOfPackages];

                for (int i = 0; i < numberOfPackages; i++) {
                    String[] packageInfo = testCasesReader.readLine().split("\\s+");
                    String pkgId = packageInfo[0];
                    int weight = Integer.parseInt(packageInfo[1]);
                    int distance = Integer.parseInt(packageInfo[2]);
                    String offerCode = packageInfo.length >= 4 ? packageInfo[3] : "";
                    double totalCost = baseDeliveryCost;
                    double discount = 0;

                    if (offerCode != "") {
                        packages[i] = new Package(pkgId, weight, distance, offerCode);
                        for (Offer offer : offers) {
                            if (offer.getCode().equals(packages[i].getOfferCode())) {
                                totalCost = packageService.applyOffer(packages[i], offer, baseDeliveryCost);
                                discount = packageService.calculatePackageCost(packages[i], baseDeliveryCost) - totalCost;
                                break;
                            }
                        }
                    } else {
                        packages[i] = new Package(pkgId, weight, distance);
                    }

                    if (!packages[i].isOfferApplied())
                        totalCost = packageService.calculatePackageCost(packages[i], baseDeliveryCost);

                    packages[i].setDiscount(discount);
                    packages[i].setTotalCost(totalCost);
                }
                if ((line = testCasesReader.readLine()) != null) {
                    String[] vehicleInfo = line.split("\\s+");
                    int numberOfVehicles = Integer.parseInt(vehicleInfo[0]);
                    int maximumSpeed = Integer.parseInt(vehicleInfo[1]);
                    int maximumCarryingWeight = Integer.parseInt(vehicleInfo[2]);

                    shippingPackages(packages, numberOfVehicles, maximumSpeed, maximumCarryingWeight, packageService);

                    for (Package orgPkg : packages) {
                        System.out.printf("%s %.0f %.0f %.2f\n", orgPkg.getId(), orgPkg.getDiscount(), orgPkg.getTotalCost(), orgPkg.getEstimatedDeliveryTime());
                    }
                } else {
                    for (Package orgPkg : packages) {
                        System.out.printf("%s %.0f %.0f\n", orgPkg.getId(), orgPkg.getDiscount(), orgPkg.getTotalCost());
                    }
                }
                System.out.println();
            }
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
