package com.example.movie_management_system.service;

import com.example.movie_management_system.dto.SeatFilterDto;
import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.repository.SeatRepository;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class SeatService {

    private final SeatRepository seatRepository;
    private final HallService hallService;
    private final ScreeningService screeningService;


    public SeatService(SeatRepository seatRepository, HallService hallService, ScreeningService screeningService) {
        this.seatRepository = seatRepository;
        this.hallService = hallService;
        this.screeningService = screeningService;
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

        Long numberOfSeats = seatRepository.countByHallId(hallId);
        if (numberOfSeats >= hall.getCapacity()) {
            throw new DataIntegrityViolationException("Hall Capacity Exceeded.");
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

        boolean hallIsChanging = !existingSeat.getHall().getId().equals(newHallId);

        if (hallIsChanging) {
            existingSeat.setHall(newHall);
        }

        Optional<Seat> foundSeat = seatRepository.findBySeatRowAndSeatColumnAndHallId(updatedSeat.getSeatRow(), updatedSeat.getSeatColumn(), newHallId);
        if (foundSeat.isPresent() && !foundSeat.get().getId().equals(existingSeat.getId())) {
            throw new DataIntegrityViolationException("There is already a seat with the same row and column.");
        }

        if (hallIsChanging) {

            Long currentSeatCount = seatRepository.countByHallId(newHallId);

            if (currentSeatCount >= newHall.getCapacity()) {
                throw new DataIntegrityViolationException("Hall Capacity Exceeded in new Hall.");
            }
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

    public Page<Seat> findAll(SeatFilterDto filter, Pageable pageable) {

        if (filter.isEmpty()) {
            return seatRepository.findAll(pageable);
        }

        Specification<Seat> spec = (root, query, cb) -> cb.conjunction();

        if (filter.getHallNameQuery() != null && !filter.getHallNameQuery().trim().isEmpty()) {
            final String hallNamePattern = "%" + filter.getHallNameQuery().trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) -> {
                Join<Seat, Hall> hallJoin = root.join("hall", JoinType.INNER);
                return cb.like(cb.lower(hallJoin.get("name")), hallNamePattern);
            });
        }
        return seatRepository.findAll(spec, pageable);
    }

    public Optional<Seat> findById(Long id) {
        return seatRepository.findById(id);
    }

    public List<Seat> findByHallId(Long hallId) {
        return seatRepository.findByHallId(hallId);
    }
}
