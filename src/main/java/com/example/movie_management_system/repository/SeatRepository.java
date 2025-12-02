package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat,Long> {
    Optional<Seat> findBySeatRowAndSeatColumnAndHallId(String row, String column, Long HallId);
}
