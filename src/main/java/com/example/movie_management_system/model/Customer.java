package com.example.movie_management_system.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL)
    private List<Ticket> tickets;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        tickets = new ArrayList<>();
    }

    public Customer() {

    }

    public String getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public List<Ticket> getTickets(){
        return tickets;
    }

    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }

}