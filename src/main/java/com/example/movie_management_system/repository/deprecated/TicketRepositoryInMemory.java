package com.example.movie_management_system.repository.deprecated;

import com.example.movie_management_system.model.Ticket;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class TicketRepositoryInMemory extends BaseRepositoryInMemory<Ticket, String> {
    public TicketRepositoryInMemory() {
        super(Ticket::getId);
    }
}
