package com.example.movie_management_system.model;

public class Ticket {
    private String id;
    private String screeningId;
    private String customerId;
    private String seatId;
    private double price;

    public Ticket(String id, String screeningId, String customerId, String seatId, double price) {
        this.id = id;
        this.screeningId = screeningId;
        this.customerId = customerId;
        this.seatId = seatId;
        this.price = price;
    }

    public String getId(){
        return id;
    }
    public String getScreeningId(){
        return screeningId;
    }
    public String getCustomerId(){
        return customerId;
    }
    public String getSeatId(){
        return seatId;
    }
    public double getPrice(){
        return price;
    }
}