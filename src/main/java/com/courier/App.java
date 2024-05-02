package com.courier;

import com.courier.model.Package;
import com.courier.model.Vehicle;
import com.courier.service.PackageService;
import com.courier.service.VehicleService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class App {
    static PackageService packageService = new PackageService();
    static VehicleService vehicleService = new VehicleService();

    public static void main(String[] args) throws IOException {
        String fileName = args[0];
        InputStream inputStream = App.class.getResourceAsStream("/" + fileName);

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            String line;

            while ((line = reader.readLine()) != null) {
                Package[] packages = packageService.readPackages(line, reader);
                if ((line = reader.readLine()) != null) {
                    String[] vehicleInfo = line.split("\\s+");
                    int numberOfVehicles = Integer.parseInt(vehicleInfo[0]);
                    Vehicle.maximumSpeed = Integer.parseInt(vehicleInfo[1]);
                    Vehicle.maximumCarryingWeight = Integer.parseInt(vehicleInfo[2]);

                    vehicleService.shipPackages(packages, numberOfVehicles);

                }
                printPackages(packages);
            }
        } catch (IOException e) {
            throw new IOException(e.getMessage());
        }


    }

    private static void printPackages(Package[] packages) {

        for (Package orgPkg : packages) {
            System.out.printf("%s %.0f %.0f ", orgPkg.getId(), orgPkg.getDiscount(), orgPkg.getTotalCost());
            if (orgPkg.getEstimatedDeliveryTime() != 0) System.out.printf("%.2f\n", orgPkg.getEstimatedDeliveryTime());
            else System.out.println();
        }
        System.out.println();
    }
}
