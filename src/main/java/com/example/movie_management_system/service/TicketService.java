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
    public Ticket save(Ticket ticket) {
        Long screeningId = ticket.getScreening().getId();
        Long customerId = ticket.getCustomer().getId();
        Long seatId = ticket.getSeat().getId();

        Screening screening = screeningService.findById(screeningId)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + screeningId + " not found."));

        Customer customer = customerService.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer with ID " + customerId + " not found."));

        Seat seat = seatService.findById(seatId)
                .orElseThrow(() -> new NoSuchElementException("Seat with ID " + seatId + " not found."));

        if(seat.getHall().getId() != screening.getHall().getId()) {
            throw new IllegalArgumentException("Seat with ID " + seatId + " is not part of the hall this screening is showing at.");
        }

        ticket.setScreening(screening);
        ticket.setCustomer(customer);
        ticket.setSeat(seat);

        return ticketRepository.save(ticket);
    }


    @Transactional
    public Ticket update(Long id, Ticket updatedTicket) {
        Ticket existingTicket = ticketRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket with ID " + id + " not found."));

        Long newScreeningId = updatedTicket.getScreening().getId();
        Long newCustomerId = updatedTicket.getCustomer().getId();
        Long newSeatId = updatedTicket.getSeat().getId();

        Screening newScreening = screeningService.findById(newScreeningId)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + newScreeningId + " not found."));

        Customer newCustomer = customerService.findById(newCustomerId)
                .orElseThrow(() -> new NoSuchElementException("Customer with ID " + newCustomerId + " not found."));

        Seat newSeat = seatService.findById(newSeatId)
                .orElseThrow(() -> new NoSuchElementException("Seat with ID " + newSeatId + " not found."));

        if(newSeat.getHall().getId() != newScreening.getHall().getId()) {
            throw new IllegalArgumentException("Seat with ID " + newSeatId + " is not part of the hall this screening is showing at.");
        }

        existingTicket.setScreening(newScreening);
        existingTicket.setCustomer(newCustomer);
        existingTicket.setSeat(newSeat);
        existingTicket.setPrice(updatedTicket.getPrice());

        return ticketRepository.save(existingTicket);
    }

    @Transactional
    public void delete(Long id) {
        ticketRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket with ID " + id + " not found"));

        ticketRepository.deleteById(id);

    }

    public List<Screening> getAvailableScreenings() {
        return screeningService.findAll();
    }

    public List<Customer> getAvailableCustomers() {
        return customerService.findAll();
    }

    public List<Seat> getAvailableSeats() {
        return seatService.findAll();
//        return seatService.findAll().stream().filter(screening -> screening.getId().equals(screeningId)).toList();
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }
    public Optional<Ticket> findById(Long id) {
        return ticketRepository.findById(id);
    }


}
