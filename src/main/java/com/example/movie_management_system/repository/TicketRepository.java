package com.example.movie_management_system.repository;

import com.example.movie_management_system.dto.TicketFilterDto;
import com.example.movie_management_system.model.Ticket;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long>, JpaSpecificationExecutor<Ticket> {
    Optional<Ticket> findBySeatIdAndScreeningId(Long seatId, Long screeningId);
    List<Ticket> findByScreeningId(Long screeningId);


}
