package com.jitesh.ratelimiter.service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.jitesh.ratelimiter.entity.Hotel;

/**
 * @version 1.0, 13-Nov-2016
 * @author jitesh
 */
@Service
public class CSVServiceImpl implements CSVService {
    private static final Logger      LOG            = LoggerFactory.getLogger(CSVServiceImpl.class);

    @Value("${hotel.csv.file}")
    private String                   filePath;

    private Map<String, List<Hotel>> cityToHotelMap = new HashMap<>();

    /**
     * This function is called on bean initialization i.e. application startup. It will load content of csv file of
     * hotel list to memory.
     */
    @PostConstruct
    public void init() {
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";
        try {
            br = new BufferedReader(new FileReader(filePath));
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] row = line.split(cvsSplitBy);
                Hotel hotel = new Hotel();
                hotel.setCity(row[0]);
                hotel.setHotelId(Integer.parseInt(row[1]));
                hotel.setRoom(row[2]);
                hotel.setPrice(Integer.parseInt(row[3]));
                String key = row[0].toLowerCase();
                if (cityToHotelMap.get(key) == null) {
                    cityToHotelMap.put(key, new ArrayList<>());
                }
                cityToHotelMap.get(key).add(hotel);
            }
        } catch (FileNotFoundException e) {
            LOG.error("File not found at {}", filePath);
        } catch (IOException e) {
            LOG.error("IOException occured ", e);
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public List<Hotel> searchByCityId(String city, String priceSort) {
        List<Hotel> hotels = cityToHotelMap.get(city.toLowerCase());
        if (priceSort.equalsIgnoreCase("asc")) {
            Collections.sort(hotels, new Comparator<Hotel>() {
                @Override
                public int compare(Hotel o1, Hotel o2) {
                    return o1.getPrice().compareTo(o2.getPrice());
                }
            });
        } else {
            Collections.sort(hotels, new Comparator<Hotel>() {
                @Override
                public int compare(Hotel o1, Hotel o2) {
                    return o2.getPrice().compareTo(o1.getPrice());
                }
            });
        }
        return hotels;
    }

}
