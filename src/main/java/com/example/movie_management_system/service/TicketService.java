package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Customer;
import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.model.Ticket;
import com.example.movie_management_system.repository.TicketRepositoryInFile;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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

    @Transactional
    public void add(String screeningID,String customerId, String seatId, double price) {
        String id = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(id, screeningID, customerId, seatId, price);
        if(screeningService.findById(screeningID).isPresent() && customerService.findById(customerId).isPresent() && seatService.findById(seatId).isPresent()) {
            ticketRepository.add(ticket);
            //screeningService.addTicket(screeningId, ticket);
            customerService.addTicket(customerId, ticket);
        }
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
    @Transactional
    public void update(String id, String screeningId, String customerId, String seatId, double price) {
        Ticket ticket = new Ticket(id, screeningId, customerId, seatId, price);
        ticketRepository.update(id, ticket);
        customerService.updateTicket(customerId, id, ticket);

    }

    @Transactional
    public boolean remove(String id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (!ticket.isPresent()) {
            throw new NoSuchElementException("Ticket with id " + id + " not found");
        }
        customerService.removeTicket(ticket.get().getCustomerId(), id);
        ticketRepository.remove(id);

        return true;

    }
    public List<Ticket> getAll() {
        return ticketRepository.getAll();
    }
    public Optional<Ticket> findById(String id) {
        return ticketRepository.findById(id);
    }


}
