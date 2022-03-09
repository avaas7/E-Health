package com.example.appointmentapplication.Home;

public class Designation {
    String email;
    String uid;
    String role;


    public Designation() {
    }

    public Designation(String email,String uid, String role) {
        this.email = email;
        this.uid = uid;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
