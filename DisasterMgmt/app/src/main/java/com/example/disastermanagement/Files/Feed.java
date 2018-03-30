package com.example.disastermanagement.Files;

/**
 * Created by Karan on 27-03-2018.
 */

public class Feed {
    private String category,description,lat,lon;
    private String image;

    public Feed(String category, String description, String lat, String lon,String image) {
        this.category = category;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.image=image;
    }
    public Feed(){}

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getLat() {
        return lat;
    }

    public String getLon() {
        return lon;
    }
}
