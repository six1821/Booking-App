package com.example.theverynewproject;

public class Booking {
    private String customerName;
    private String date;

    public Booking() {
        // Required default constructor for Firebase
    }

    public Booking(String customerName, String date) {
        this.customerName = customerName;
        this.date = date;
    }

    public String getCustomerName() {
        return customerName;
    }

    public String getDate() {
        return date;
    }
}
