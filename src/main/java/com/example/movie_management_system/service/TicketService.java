package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Ticket;
import com.example.movie_management_system.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;

    public TicketService(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }


    public Ticket add(String screeningID,String customerId, String seatId, double price) {
        String id = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(id, screeningID, customerId, seatId, price);
        return ticketRepository.add(ticket);
    }

    public void remove(String id) {
        ticketRepository.remove(id);
    }
    public List<Ticket> getAll() {
        return ticketRepository.getAll();
    }
    public Optional<Ticket> findById(String id) {
        return ticketRepository.findById(id);
    }
}
