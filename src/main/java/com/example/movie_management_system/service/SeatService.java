package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.repository.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SeatService {
    @Autowired
    private SeatRepository seatRepository;

    public Seat addSeat(String hallId, String seatRow, String seatColumn ) {
        String id = UUID.randomUUID().toString();
        Seat seat = new Seat(id, hallId, seatRow, seatColumn);
        return seatRepository.save(seat);
    }
    public void removeSeat(String id) {
        seatRepository.deleteById(id);
    }
    public List<Seat> getAllSeats() {
        return seatRepository.findAll();
    }
    public Optional<Seat> findById(String id) {
        return seatRepository.findById(id);
    }
}

