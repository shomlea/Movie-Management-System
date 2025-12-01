package com.example.movie_management_system.model;

import jakarta.persistence.*;

@Entity
public class Ticket {
    @Id
    private String id;

    @Column(nullable = false)
    private String screeningId;

    @Column(nullable = false)
    private String customerId;

    @Column(nullable = false)
    private String seatId;

    @Column(nullable = false)
    private double price;

    public Ticket(String id, String screeningId, String customerId, String seatId, double price) {
        this.id = id;
        this.screeningId = screeningId;
        this.customerId = customerId;
        this.seatId = seatId;
        this.price = price;
    }

    public Ticket() {

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

    public void setId(String id) {
        this.id = id;
    }
    public void setScreeningId(String screeningId) {
        this.screeningId = screeningId;
    }
    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
    public void setSeatId(String seatId) {
        this.seatId = seatId;
    }
}