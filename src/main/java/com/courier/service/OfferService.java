package com.courier.service;

import com.courier.App;
import com.courier.model.Offer;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class OfferService {
    public static List<Offer> loadOffers() {
        InputStream inputStream = App.class.getResourceAsStream("/OfferCodes.txt");
        List<Offer> offers = null;

            try (BufferedReader offerCodesReader = new BufferedReader(new InputStreamReader(inputStream))) {
                offers = new ArrayList<>();
                offerCodesReader.readLine();
                String line;
                while ((line = offerCodesReader.readLine()) != null) {
                    String[] offerInfo = line.split("\\s+");
                    offers.add(new Offer(offerInfo[0], Integer.parseInt(offerInfo[1]),
                            Integer.parseInt(offerInfo[2]),
                            Integer.parseInt(offerInfo[3]),
                            Integer.parseInt(offerInfo[4]),
                            Integer.parseInt(offerInfo[5])));
                }
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        return offers;
        }

}

