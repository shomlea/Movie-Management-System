package com.example.movie_management_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Positive;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "screening_id", nullable = false)
    private Screening screening;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne
    @JoinColumn(name = "seat_id", nullable = false)
    private Seat seat;

    @Column(nullable = false)
    @Positive(message = "Price should be a positive number")
    private double price;

    public Ticket(Screening screening, Customer customer, Seat seat, double price) {
        this.screening = screening;
        this.customer = customer;
        this.seat = seat;
        this.price = price;
    }

    public Ticket() {

    }

    public Long getId(){
        return id;
    }
    public Screening getScreening(){
        return screening;
    }
    public Customer getCustomer(){
        return customer;
    }
    public Seat getSeat(){
        return seat;
    }
    public double getPrice(){
        return price;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setScreening(Screening screening) {
        this.screening = screening;
    }
    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
    public void setSeat(Seat seat) {
        this.seat = seat;
    }
    public void setPrice(double price) {this.price = price;}
}