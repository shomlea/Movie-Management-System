package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Customer;
import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.model.Ticket;
import com.example.movie_management_system.repository.TicketRepositoryInFile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepositoryInFile ticketRepository;
    private final ScreeningService screeningService;
    private final CustomerService customerService;
    private final SeatService seatService;

    public TicketService(TicketRepositoryInFile ticketRepository, ScreeningService screeningService,  CustomerService customerService, SeatService seatService) {
        this.ticketRepository = ticketRepository;
        this.screeningService = screeningService;
        this.customerService = customerService;
        this.seatService = seatService;
    }


    public void add(String screeningID,String customerId, String seatId, double price) {
        String id = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(id, screeningID, customerId, seatId, price);
        ticketRepository.add(ticket);
    }

    public List<Screening> getAvailableScreenings() {
        return screeningService.getAll();
    }

    public List<Customer> getAvailableCustomers() {
        return customerService.getAll();
    }

    public List<Seat> getAvailableSeats(String screeningID) {
        return seatService.getAll().stream().filter(screening -> screening.getId().equals(screeningID)).toList();
    }

    public boolean update(String id, String screeningId, String customerId, String seatId, double price) {
        Ticket ticket = new Ticket(id, screeningId, customerId, seatId, price);
        return ticketRepository.update(id, ticket);
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
