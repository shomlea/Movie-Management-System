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
    public Ticket save(Long screeningId,Long customerId, Long seatId, double price) {
        Screening screening = screeningService.findById(screeningId)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + screeningId + " not found."));

        Customer customer = customerService.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer with ID " + customerId + " not found."));

        Seat seat = seatService.findById(seatId)
                .orElseThrow(() -> new NoSuchElementException("Seat with ID " + seatId + " not found."));


        Ticket ticket = new Ticket(screening, customer, seat, price);

        return ticketRepository.save(ticket);
    }

    public List<Screening> getAvailableScreenings() {
        return screeningService.findAll();
    }

    public List<Customer> getAvailableCustomers() {
        return customerService.findAll();
    }

    public List<Seat> getAvailableSeats(Long screeningId) {
        return seatService.findAll().stream().filter(screening -> screening.getId().equals(screeningId)).toList();
    }
    @Transactional
    public Ticket update(Long id, Long screeningId, Long customerId, Long seatId, double price) {
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket with ID " + id + " not found."));

        Screening newScreening = screeningService.findById(screeningId)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + screeningId + " not found."));

        Customer newCustomer = customerService.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer with ID " + customerId + " not found."));

        Seat newSeat = seatService.findById(seatId)
                .orElseThrow(() -> new NoSuchElementException("Seat with ID " + seatId + " not found."));

        existingTicket.setScreening(newScreening);
        existingTicket.setCustomer(newCustomer);
        existingTicket.setSeat(newSeat);
        existingTicket.setPrice(price);

        return ticketRepository.save(existingTicket);
    }

    @Transactional
    public void delete(Long id) {
        ticketRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket with ID " + id + " not found"));

        ticketRepository.deleteById(id);

    }
    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }


}
