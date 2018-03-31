package com.example.sihsmsreceiver;

public class Resource {

    String amount ,description,item,name,lat,lon,phone,area,datetime;

    public Resource(String amount, String description, String item, String name, String lat, String lon, String phone, String area,String datetime) {
        this.amount = amount;
        this.description = description;
        this.item = item;
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.phone = phone;
        this.area = area;
        this.datetime=datetime;
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

    public String getDatetime() {
        return datetime;
    }

    public String getLon() {
        return lon;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return area+amount+description+item+name+lat+lon+phone+" "+datetime;
    }
}

