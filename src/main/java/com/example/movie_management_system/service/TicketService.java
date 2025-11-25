package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Customer;
import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.model.Ticket;
import com.example.movie_management_system.repository.TicketRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final ScreeningService screeningService;
    private final CustomerService customerService;
    private final SeatService seatService;

    public TicketService(TicketRepository ticketRepository, ScreeningService screeningService,  CustomerService customerService, SeatService seatService) {
        this.ticketRepository = ticketRepository;
        this.screeningService = screeningService;
        this.customerService = customerService;
        this.seatService = seatService;
    }

    @Transactional
    public void save(String screeningID,String customerId, String seatId, double price) {
        String id = UUID.randomUUID().toString();
        Ticket ticket = new Ticket(id, screeningID, customerId, seatId, price);
        if(screeningService.findById(screeningID).isPresent() && customerService.findById(customerId).isPresent() && seatService.findById(seatId).isPresent()) {
            ticketRepository.save(ticket);
            //screeningService.addTicket(screeningId, ticket);
            customerService.addTicket(customerId, ticket);
        }
    }

    public List<Screening> getAvailableScreenings() {
        return screeningService.findAll();
    }

    public List<Customer> getAvailableCustomers() {
        return customerService.findAll();
    }

    public List<Seat> getAvailableSeats(String screeningID) {
        return seatService.findAll().stream().filter(screening -> screening.getId().equals(screeningID)).toList();
    }
    @Transactional
    public void update(String id, String screeningId, String customerId, String seatId, double price) {
        Ticket ticket = new Ticket(id, screeningId, customerId, seatId, price);
        ticketRepository.save(ticket);
        customerService.updateTicket(customerId, id, ticket);

    }

    @Transactional
    public boolean delete(String id) {
        Optional<Ticket> ticket = ticketRepository.findById(id);
        if (!ticket.isPresent()) {
            throw new NoSuchElementException("Ticket with id " + id + " not found");
        }
        customerService.removeTicket(ticket.get().getCustomerId(), id);
        ticketRepository.deleteById(id);

        return true;

    }
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
    public Optional<Ticket> findById(String id) {
        return ticketRepository.findById(id);
    }


}
