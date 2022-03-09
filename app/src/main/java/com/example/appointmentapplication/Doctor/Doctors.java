package com.example.appointmentapplication.Doctor;

public class Doctors {

    String email;
    String pass;
    String fullname;
    String address;
    String department;
    long phoneno;
    String position;


    public Doctors() {
    }

    public Doctors(String email, String pass, String fullname,String address, long phoneno,String department) {
        this.email = email;
        this.pass = pass;
        this.fullname = fullname;
        this.address = address;
        this.phoneno = phoneno;
        this.department= department;
        this.position = "Medical Officer";

    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
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

