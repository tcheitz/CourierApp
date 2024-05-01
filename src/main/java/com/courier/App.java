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
import static com.courier.service.PackageService.readPackage;
import static com.courier.service.VehicleService.shipPackages;


public class App {
    static PackageService packageService = new PackageService();

    public static void main(String[] args) throws IOException {
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
                    packages[i]=readPackage(testCasesReader, offers, baseDeliveryCost);
                }
                printPackages(packages, testCasesReader);
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }
    }

    private static void printPackages(Package[] packages, BufferedReader testCasesReader) throws IOException {
        String line;
        if ((line=testCasesReader.readLine()) != null) {
            String[] vehicleInfo = line.split("\\s+");
            int numberOfVehicles = Integer.parseInt(vehicleInfo[0]);
            int maximumSpeed = Integer.parseInt(vehicleInfo[1]);
            int maximumCarryingWeight = Integer.parseInt(vehicleInfo[2]);

            shipPackages(packages, numberOfVehicles, maximumSpeed, maximumCarryingWeight);

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
}
