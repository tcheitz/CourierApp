package com.courier.service;

import com.courier.model.Package;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.courier.service.PackageService.filterPackages;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilteringPackageTests {
    static List<Package> packages = new ArrayList<>();
    PackageService packageService = new PackageService();
    static double maximumCarryingWeight;

    //    @BeforeAll
//    static void setup(){
//        packages.add(new Package("PKG1",50,30,"OFR001"));
//        packages.add((new Package("PKG2",75,125,"OFR008")));
//        packages.add(new Package("PKG3",175,100,"OFR003"));
//        packages.add(new Package("PKG4",110,60,"OFR002"));
//        packages.add(new Package("PKG5",155,95,"NA"));
//        maximumCarryingWeight=200;
//    }
    @Test
    void shouldFilterMorePackagesThenMoreWeights() {
        packages.add(new Package("PKG1", 50, 30, "OFR001"));
        packages.add((new Package("PKG2", 75, 125, "OFR008")));
        packages.add(new Package("PKG3", 175, 100, "OFR003"));
        packages.add(new Package("PKG4", 110, 60, "OFR002"));
        packages.add(new Package("PKG5", 155, 95, "NA"));
        maximumCarryingWeight = 200;
        String[] packageIds = {"PKG4", "PKG2", "PKG3", "PKG5", "PKG1"};
        List<String> filteredPackageIds = new ArrayList<>();
        while (!packages.isEmpty()) {
            List<Package> packageList = filterPackages(packages, maximumCarryingWeight);
            for (Package aPackage : packageList) {
                filteredPackageIds.add(aPackage.getId());
                packages.removeIf(p -> p.getId() == aPackage.getId());
            }
        }
        assertEquals(Arrays.asList(packageIds), filteredPackageIds);


    }
    @Test
    void shouldFilterLeastDistanceIfEqualweights(){
        packages.add(new Package("PKG1",50,50));
        packages.add(new Package("PKG2",50,120));
        packages.add(new Package("PKG3",50,100));
        maximumCarryingWeight = 130;
        String[] packageIds = {"PKG1", "PKG3", "PKG2"};
        List<String> filteredPackageIds = new ArrayList<>();
        while (!packages.isEmpty()) {
            List<Package> packageList = filterPackages(packages, maximumCarryingWeight);
            for (Package aPackage : packageList) {
                filteredPackageIds.add(aPackage.getId());
                packages.removeIf(p -> p.getId() == aPackage.getId());
            }
        }
        assertEquals(Arrays.asList(packageIds), filteredPackageIds);
    }
}
