package com.example.sihsmsreceiver;

public class Feed {
    String category, description, latitude,longitude,time,area,uid;

    public Feed(String category, String description, String latitude, String longitude, String time, String area, String uid) {
        this.category = category;
        this.description = description;
        this.latitude = latitude;
        this.longitude = longitude;
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
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTime() {
        return time;
    }

    @Override
    public String toString() {
        return category+" "+description+" "+latitude+" "+longitude+" "+time+" "+uid+" "+area;
    }
}
