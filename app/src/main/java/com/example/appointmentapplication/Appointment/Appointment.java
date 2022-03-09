package com.example.appointmentapplication.Appointment;

public class Appointment {

    String patientName;
    String date;
    String department;
    String uid;
    String key;
    String status;
    String prescription;
    String uid_status;

    public Appointment() {
    }

    public Appointment(String date, String department,String uid,String key,String status,String patientName,String uid_status) {
        this.date = date;
        this.department = department;
        this.uid = uid;
        this.key = key;
        this.status= status;
        this.patientName = patientName;
        this.prescription = "not given yet";
        this.uid_status = uid_status;
    }


    public String getUid_status() {
        return uid_status;
    }

    public void setUid_status(String uid_status) {
        this.uid_status = uid_status;
    }

    public String getPrescription() {
        return prescription;
    }

    public void setPrescription(String prescription) {
        this.prescription = prescription;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }
}
