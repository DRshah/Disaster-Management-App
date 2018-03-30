package com.example.disastermanagement.Files;

/**
 * Created by Darsh_Shah on 30-03-2018.
 */

public class Contacts {
    private String servicename;

    private String contactinformation;

    public Contacts(String servicename, String contactinformation) {
        this.servicename = servicename;
        this.contactinformation = contactinformation;
    }

    public String getServicename() {
        return servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

    public String getContactinformation() {
        return contactinformation;
    }

    public void setContactinformation(String contactinformation) {
        this.contactinformation = contactinformation;
    }
}
