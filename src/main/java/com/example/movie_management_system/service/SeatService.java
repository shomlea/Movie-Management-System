package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.repository.SeatRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Seat save(Seat seat) {
        Long hallId = seat.getHall().getId();

        Hall hall = hallService.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        Optional<Seat> foundSeat = seatRepository.findBySeatRowAndSeatColumnAndHallId(seat.getSeatRow(), seat.getSeatColumn(), hallId);
        if (foundSeat.isPresent()) {
            throw new DataIntegrityViolationException("There is already a seat with the same row and column.");
        }

        seat.setHall(hall);

        return seatRepository.save(seat);
    }

    @Transactional
    public Seat update(Long id, Seat updatedSeat) {
        Seat existingSeat = seatRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Seat with ID " + id + " not found."));

        Long newHallId = updatedSeat.getHall().getId();

        Hall newHall = hallService.findById(newHallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + newHallId + " not found."));

        existingSeat.setSeatRow(updatedSeat.getSeatRow());
        existingSeat.setSeatColumn(updatedSeat.getSeatColumn());

        if (!existingSeat.getHall().getId().equals(newHallId)) {
            existingSeat.setHall(newHall);
        }

        Optional<Seat> foundSeat = seatRepository.findBySeatRowAndSeatColumnAndHallId(updatedSeat.getSeatRow(), updatedSeat.getSeatColumn(), newHallId);
        if (foundSeat.isPresent() && !foundSeat.get().getId().equals(existingSeat.getId())) {
            throw new DataIntegrityViolationException("There is already a seat with the same row and column.");
        }

        return seatRepository.save(existingSeat);
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
