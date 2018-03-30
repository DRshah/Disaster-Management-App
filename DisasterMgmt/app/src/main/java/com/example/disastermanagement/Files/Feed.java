package com.example.disastermanagement.Files;

/**
 * Created by Karan on 27-03-2018.
 */

public class Feed {
    public String category;
    private String description;
    private String lat;
    private String lon;
    private String image;
    private String fid;
    //private String time;
    private String datetime;
    private String area;
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public Feed(String category, String description, String lat, String lon, String image,String datetime,String fid,String area) {
        this.category = category;
        this.description = description;
        this.lat = lat;
        this.lon = lon;
        this.image = image;
        this.datetime=datetime;
        this.fid=fid;
        this.area=area;
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


    public String getDateTime(){
        return datetime;
    }

    public String getFid(){
        return fid;
    }

    public String getArea(){
        return area;
    }

}
