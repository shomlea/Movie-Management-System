package com.example.movie_management_system.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    private String id;
    @Column(nullable = false)
    private String name;
    @OneToMany(mappedBy = "customerId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets= new ArrayList<>();

    public Customer(String id, String name) {
        this.id = id;
        this.name = name;
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

    public void setId(String id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setTickets(List<Ticket> tickets){
        this.tickets = tickets;
    }

    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }
    public boolean removeTicket(String ticketId){
        return tickets.removeIf(ticket -> ticketId.equals(ticket.getId()));
    }
    public boolean updateTicket(String ticketId, Ticket ticket){
        int index = -1;
        for (int i = 0; i < tickets.size(); i++) {
            if (ticketId.equals(tickets.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            tickets.set(index, ticket);
            return true;
        }
        return false;
    }

}