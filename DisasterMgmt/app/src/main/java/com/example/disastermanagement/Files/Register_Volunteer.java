package com.example.disastermanagement.Files;
import com.google.firebase.database.Exclude;
import java.util.HashMap;
import java.util.Map;
public class Register_Volunteer {
    public String num,email,lat,lng;
    public Register_Volunteer()
    {}
    public Register_Volunteer(String num, String email, String lat, String lng) {
        this.num = num;
        this.email = email;
        this.lat = lat;
        this.lng = lng;
    }
    @Exclude
    public Map<String, Object> toMap()
    {
        HashMap<String, Object> result=new HashMap<>();
        result.put("num",num);
        result.put("email",email);
        result.put("lat",lat);
        result.put("lng",lng);
        return result;
    }
    public String getNum() {
        return num;
    }
    public void setNum(String num) {
        this.num = num;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getLat() {
        return lat;
    }
    public void setLat(String lat) {
        this.lat = lat;
    }
    public String getLng() {
        return lng;
    }
    public void setLng(String lng) {
        this.lng = lng;
    }
}