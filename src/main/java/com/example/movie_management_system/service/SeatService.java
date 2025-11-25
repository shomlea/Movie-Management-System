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
    public void save(String hallId, String seatRow, String seatColumn) {

        Hall hall = hallService.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with id " + hallId + " not found"));

        String id = UUID.randomUUID().toString();
        Seat seat = new Seat(id, hallId, seatRow, seatColumn);

        // Add seat to hall (hall is the owning side logically)
        hall.addSeat(seat);

        // Persist seat
        seatRepository.save(seat);
    }

    @Transactional
    public void update(String id, String hallId, String seatRow, String seatColumn) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Seat with id " + id + " not found"));

        seat.setSeatRow(seatRow);
        seat.setSeatColumn(seatColumn);

        if (!seat.getHallId().equals(hallId)) {
            Hall oldHall = hallService.findById(seat.getHallId())
                    .orElseThrow(() -> new NoSuchElementException("Old hall not found"));

            Hall newHall = hallService.findById(hallId)
                    .orElseThrow(() -> new NoSuchElementException("New hall not found"));

            oldHall.removeSeat(id);
            newHall.addSeat(seat);

            seat.setHallId(hallId);
        }

    }

    @Transactional
    public void delete(String id) {
        Seat seat = seatRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Seat with id " + id + " not found"));

        Hall hall = hallService.findById(seat.getHallId())
                .orElseThrow(() -> new NoSuchElementException("Hall not found"));

        hall.removeSeat(id);

        seatRepository.deleteById(id);
    }

    public List<Hall> getAvailableHalls() {
        return hallService.findAll();
    }

    public List<Seat> getAll() {
        return seatRepository.findAll();
    }

    @Transactional
    public Optional<Seat> findById(String id) {
        return seatRepository.findById(id);
    }
}
