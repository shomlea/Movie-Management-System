package com.example.movie_management_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Customer name is required.")
    private String name;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets= new ArrayList<>();

    public Customer(String name) {
        this.name = name;
    }

    public Customer() {

    }

    public Long getId(){
        return id;
    }
    public String getName(){
        return name;
    }
    public List<Ticket> getTickets(){
        return tickets;
    }

    public void setId(Long id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
    public void setTickets(List<Ticket> tickets){
        this.tickets = tickets;
    }

//    public void addTicket(Ticket ticket){
//        tickets.add(ticket);
//    }
//    public boolean removeTicket(Long ticketId){
//        return tickets.removeIf(ticket -> ticketId.equals(ticket.getId()));
//    }
//    public boolean updateTicket(Long ticketId, Ticket ticket){
//        int index = -1;
//        for (int i = 0; i < tickets.size(); i++) {
//            if (ticketId.equals(tickets.get(i).getId())) {
//                index = i;
//                break;
//            }
//        }
//        if (index != -1) {
//            tickets.set(index, ticket);
//            return true;
//        }
//        return false;
//    }

}