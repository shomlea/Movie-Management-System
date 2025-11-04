package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.repository.SeatRepositoryInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SeatService {
    @Autowired
    private SeatRepositoryInMemory seatRepository;

    public SeatService(SeatRepositoryInMemory seatRepository) {
        this.seatRepository = seatRepository;
    }

    public void save(String hallId, String seatRow, String seatColumn ) {
        String id = UUID.randomUUID().toString();
        Seat seat = new Seat(id, hallId, seatRow, seatColumn);
        seatRepository.add(seat);
    }
    public void remove(String id) {
        seatRepository.remove(id);
    }
    public List<Seat> getAll() {
        return seatRepository.getAll();
    }
    public Optional<Seat> findById(String id) {
        return seatRepository.findById(id);
    }
}

