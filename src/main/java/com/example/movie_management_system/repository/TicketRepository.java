package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.Ticket;

import java.util.ArrayList;
import java.util.List;

public class TicketRepository {
    private List<Ticket> tickets;

    public TicketRepository() {
        tickets = new ArrayList<>();
    }

    public List<Ticket> getAllTickets() {
        return tickets;
    }
    public Ticket getTicketById(String id) {
        for (Ticket ticket : tickets) {
            if (ticket.getId().equals(id)) {
                return ticket;
            }
        }
        return null;
    }

    public void addTicket(Ticket ticket) {
        tickets.add(ticket);
    }
}