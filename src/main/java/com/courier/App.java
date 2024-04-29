package com.courier;

import com.courier.model.Offer;
import com.courier.model.Package;
import com.courier.model.Vehicle;
import com.courier.service.PackageService;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class App {
    static PackageService packageService = new PackageService();

    public static void main(String[] args) throws FileNotFoundException {
        String fileName = args[0];
        File inputFilesDir = new File(System.getProperty("user.dir") + "\\src\\main\\resources\\");
        File testCaseFile = new File(inputFilesDir, fileName);
        File offerCodes = new File(inputFilesDir, "OfferCodes.txt");
        Scanner testCasesReader = new Scanner(testCaseFile);
        Scanner offerCodesReader = new Scanner(offerCodes);
        List<Offer> offers = new ArrayList<>();
        offerCodesReader.nextLine();
        while (offerCodesReader.hasNextLine()) {
            String[] offerInfo = offerCodesReader.nextLine().split("\\s+");
            offers.add(new Offer(offerInfo[0], Integer.parseInt(offerInfo[1]),
                    Integer.parseInt(offerInfo[2]),
                    Integer.parseInt(offerInfo[3]),
                    Integer.parseInt(offerInfo[4]),
                    Integer.parseInt(offerInfo[5])));
        }
        offerCodesReader.close();
        double baseDeliveryCost = testCasesReader.nextInt();
        int numberOfPackages = testCasesReader.nextInt();
        Package[] packages = new Package[numberOfPackages];
        testCasesReader.nextLine();
        for (int i = 0; i < numberOfPackages; i++) {
            String[] packageInfo = testCasesReader.nextLine().split(" ");
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
        if (testCasesReader.hasNextLine()) {
            String[] vehicleInfo = testCasesReader.nextLine().split(" ");
            int numberOfVehicles = Integer.parseInt(vehicleInfo[0]);
            int maxSpeed = Integer.parseInt(vehicleInfo[1]);
            int maxCarriableWeight = Integer.parseInt(vehicleInfo[2]);
            List<Package> newPack = new ArrayList<>(Arrays.asList(packages));
            Vehicle[] vehicles = new Vehicle[numberOfVehicles];
            double currentTime = 0;

            for (int i = 0; i < numberOfVehicles && newPack.size() != 0; i++) {
                vehicles[i] = new Vehicle(maxSpeed);
                packageService.loadPackages(vehicles[i], newPack, maxCarriableWeight, packages, currentTime);
            }
            while (newPack.size() != 0) {

                Vehicle nextVehicle = Arrays.stream(vehicles).min(Comparator.comparing(v -> v.getReturnTime())).get();
                for (Vehicle vehicle : vehicles) {
                    if (vehicle != nextVehicle)
                        vehicle.setReturnTime(vehicle.getReturnTime() - nextVehicle.getReturnTime());

                }
                currentTime = currentTime + nextVehicle.getReturnTime();
                packageService.loadPackages(nextVehicle, newPack, maxCarriableWeight, packages, currentTime);
            }
            for (Package orgPkg : packages) {
                System.out.printf("%s %.0f %.0f %.2f\n", orgPkg.getId(), orgPkg.getDiscount(), orgPkg.getTotalCost(), orgPkg.getEstimatedDeliveryTime());
            }
        }else{
            for (Package orgPkg : packages) {
                System.out.printf("%s %.0f %.0f\n", orgPkg.getId(), orgPkg.getDiscount(), orgPkg.getTotalCost());
            }
        }
        testCasesReader.close();
    }
}
