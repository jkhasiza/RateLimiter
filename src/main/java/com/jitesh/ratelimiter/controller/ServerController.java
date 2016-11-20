package com.jitesh.ratelimiter.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.jitesh.ratelimiter.entity.Hotel;
import com.jitesh.ratelimiter.response.ResponseBean;
import com.jitesh.ratelimiter.service.CSVService;
import com.jitesh.ratelimiter.service.KeyValidatorService;

@Controller
public class ServerController {

    @Autowired
    private CSVService          csvService;

    @Autowired
    private KeyValidatorService keyValidator;

    /**
     * Healthcheck URL
     */
    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public String healthCheck() {
        return "OK";
    }

    /**
     * This api checks the validity of api key and searches the hotels based upon city and sorts by hotel prices
     * 
     * @param city
     * @param key
     * @param priceSort
     * @return
     */
    @RequestMapping(value = "/getHotelsByCity", method = RequestMethod.GET)
    @ResponseBody
    public ResponseBean<?> getHotelsByCity(@RequestParam(value = "city") String city, @RequestParam(value = "apiKey") String key,
            @RequestParam(value = "priceSort", required = false, defaultValue = "asc") String priceSort) {
        long currTime = (new Date()).getTime();
        boolean isValid = keyValidator.isKeyValid(currTime, key);
        if (isValid) {
            List<Hotel> hotels = csvService.searchByCityId(city, priceSort);
            return new ResponseBean<List<Hotel>>(hotels);
        }
        return new ResponseBean<>("ERROR", "QUERY_LIMIT_EXCEEDED", null);
    }
}
