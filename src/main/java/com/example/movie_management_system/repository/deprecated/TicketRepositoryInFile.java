package com.example.movie_management_system.repository.deprecated;

import com.example.movie_management_system.model.Ticket;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class TicketRepositoryInFile extends BaseRepositoryInFile<Ticket, String>{
    public TicketRepositoryInFile() {
        super(Ticket::getId, "data/tickets.json", Ticket.class);
    }
}
