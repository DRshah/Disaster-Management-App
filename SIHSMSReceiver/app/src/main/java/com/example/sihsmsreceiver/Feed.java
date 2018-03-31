package com.example.sihsmsreceiver;

public class Feed {
    String category, description, lat,lon,time,area,uid;

    public Feed(String category, String description, String latitude, String longitude, String time, String area, String uid) {
        this.category = category;
        this.description = description;
        this.lat = latitude;
        this.lon = longitude;
        this.time = time;
        this.area = area;
        this.uid = uid;
    }

    public String getArea() {
        return area;
    }

    public String getUid() {
        return uid;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getLatitude() {
        return lat;
    }

    public String getLongitude() {
        return lon;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return category+" "+description+" "+lat+" "+lon+" "+time+" "+uid+" "+area;
    }
}
