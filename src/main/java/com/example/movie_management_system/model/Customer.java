package com.example.movie_management_system.model;

import java.util.ArrayList;
import java.util.List;

public class Customer {
    private String id;
    private String name;
    private List<Ticket> tickets;

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
        tickets = new ArrayList<>();
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