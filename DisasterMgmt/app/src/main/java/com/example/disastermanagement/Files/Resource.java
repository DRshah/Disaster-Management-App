package com.example.disastermanagement.Files;

public class Resource {

    String amount ,description,item,name,lat,lon,phone,area;

    public Resource(String amount, String description, String item, String name, String lat, String lon, String phone, String area) {
        this.amount = amount;
        this.description = description;
        this.item = item;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.phone = phone;
        this.area = area;
    }

    public String getArea() {
        return area;
    }

    public String getAmount() {
        return amount;
    }

    public String getDescription() {
        return description;
    }

    public String getItem() {
        return item;
    }

    public String getName() {
        return name;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return area+amount+description+item+name+lat+lon+phone;
    }
}