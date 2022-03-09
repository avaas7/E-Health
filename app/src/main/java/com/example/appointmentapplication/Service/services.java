package com.example.appointmentapplication.Service;

import java.security.Provider;

public class services {

    String name;
    String desc;
    int imageLink;

    public services(String name, String desc, int imageLink) {
        this.name = name;
        this.desc = desc;
        this.imageLink = imageLink;
    }

    public services() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getImageLink() {
        return imageLink;
    }

    public void setImageLink(int imageLink) {
        this.imageLink = imageLink;
    }
}
