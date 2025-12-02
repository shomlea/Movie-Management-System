package com.example.movie_management_system.converter;

import com.example.movie_management_system.model.Ticket;
import com.example.movie_management_system.service.TicketService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

@Component
public class TicketConverter implements Converter<Long, Ticket> {

    private final TicketService ticketService;

    public TicketConverter(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @Override
    public Ticket convert(Long id) {
        if (id == null) return null;

        return ticketService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Ticket with ID " + id + " not found."));
    }
}