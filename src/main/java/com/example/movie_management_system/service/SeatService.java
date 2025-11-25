package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.repository.deprecated.SeatRepositoryInFile;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
    @Transactional
    public void add(String hallId, String seatRow, String seatColumn ) {
        String id = UUID.randomUUID().toString();
        Seat seat = new Seat(id, hallId, seatRow, seatColumn);
        if(hallService.findById(hallId).isPresent()) {
            seatRepository.add(seat);
            hallService.addSeat(hallId, seat);
        }
    }
    @Transactional
    public boolean update(String id, String hallId, String seatRow, String seatColumn) {
        Seat seat = new Seat(id, hallId, seatRow, seatColumn);
        hallService.updateSeat(hallId, id, seat);
        seatRepository.update(id, seat);
        return true;
    }

    @Transactional
    public boolean remove(String id) {
        Optional<Seat> seat = seatRepository.findById(id);
        if (!seat.isPresent()) {
            throw new NoSuchElementException("Seat with id " + id + " not found");
        }
        hallService.removeSeat(seat.get().getHallId(), id);
        seatRepository.remove(id);

        return true;
    }

    public List<Hall> getAvailableHalls(){
        return hallService.getAll();
    }

    public List<Seat> getAll() {
        return seatRepository.getAll();
    }
    public Optional<Seat> findById(String id) {
        return seatRepository.findById(id);
    }
}

