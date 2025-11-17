package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.repository.HallRepositoryInFile;
import com.example.movie_management_system.repository.SeatRepositoryInFile;
import com.example.movie_management_system.repository.deprecated.SeatRepositoryInMemory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class SeatService {
    @Autowired
    private final SeatRepositoryInFile seatRepository;
    private final HallService hallService;

    public SeatService(SeatRepositoryInFile seatRepository, HallService hallService)
    {
        this.seatRepository = seatRepository;
        this.hallService = hallService;
    }

    public void add(String hallId, String seatRow, String seatColumn ) {
        String id = UUID.randomUUID().toString();
        Seat seat = new Seat(id, hallId, seatRow, seatColumn);
        seatRepository.add(seat);
    }

    public boolean update(String id, String hallId, String seatRow, String seatColumn) {
        Seat seat = new Seat(id, hallId, seatRow, seatColumn);
        return seatRepository.update(id, seat);
    }

    public List<Hall> getAvailableHalls(){
        return hallService.getAll();
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

