package com.example.appointmentapplication.Home;

import com.example.appointmentapplication.Authentication.SignUpActivity;

public class User {

    String email;
    String pass;
    String fullname;
    int age;
    String address;
    long phoneno;
    String role;
    public User() {
    }

    public User(String email, String pass, String fullname, int age, String address, long phoneno) {
        this.email = email;
        this.pass = pass;
        this.fullname = fullname;
        this.age = age;
        this.address = address;
        this.phoneno = phoneno;
        this.role = SignUpActivity.ROLE_PATIENT;
    }


    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public long getPhoneno() {
        return phoneno;
    }

    public void setPhoneno(long phoneno) {
        this.phoneno = phoneno;
    }
}
