package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Ticket;
import com.example.movie_management_system.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }


    public Ticket addTicket(String screeningID,String customerId, String seatId, int price) {
        String id = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(id, screeningID, customerId, seatId, price);
        return ticketRepository.save(ticket);
    }

    public void removeTicket(Ticket ticket) {
        ticketRepository.delete(ticket);
    }
    public List<Ticket> getAllTickets() {
        return ticketRepository.findAll();
    }
}
