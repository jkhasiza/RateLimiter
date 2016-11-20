package com.jitesh.ratelimiter.entity;

/**
 * @version 1.0, 17-Nov-2016
 * @author jitesh
 */
public class Hotel {
    private String  city;
    private String  room;
    private Integer hotelId;
    private Integer price;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public Integer getHotelId() {
        return hotelId;
    }

    public void setHotelId(Integer hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

}
