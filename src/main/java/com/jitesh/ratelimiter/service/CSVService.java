package com.jitesh.ratelimiter.service;

import java.util.List;

import com.jitesh.ratelimiter.entity.Hotel;

public interface CSVService {

    List<Hotel> searchByCityId(String city, String priceSort);

}
