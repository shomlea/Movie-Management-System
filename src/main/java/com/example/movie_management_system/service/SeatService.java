package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final HallService hallService;

    public SeatService(SeatRepository seatRepository, HallService hallService) {
        this.seatRepository = seatRepository;
        this.hallService = hallService;
    }

    @Transactional
    public Seat save(Long hallId, String seatRow, String seatColumn) {

        Hall hall = hallService.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with id " + hallId + " not found"));

        Seat seat = new Seat(hall, seatRow, seatColumn);

        return seatRepository.save(seat);
    }

    @Transactional
    public Seat update(Long id, Long hallId, String seatRow, String seatColumn) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Seat with id " + id + " not found"));

        Hall newHall = hallService.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with id " + hallId + " not found"));

        seat.setSeatRow(seatRow);
        seat.setSeatColumn(seatColumn);

        if (!seat.getHall().getId().equals(hallId)) {
            seat.setHall(newHall);
        }

        return seatRepository.save(seat);

    }

    @Transactional
    public void delete(Long id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Seat with id " + id + " not found"));

        seatRepository.deleteById(id);
    }

    public List<Hall> getAvailableHalls() {
        return hallService.findAll();
    }

    public List<Seat> findAll() {
        return seatRepository.findAll();
    }


    public Optional<Seat> findById(Long id) {
        return seatRepository.findById(id);
    }
}
