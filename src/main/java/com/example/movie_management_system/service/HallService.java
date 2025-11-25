package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.repository.HallRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class HallService {

    private final HallRepository hallRepository;
    private final TheatreService theatreService;

    public HallService(HallRepository hallRepository, TheatreService theatreService) {
        this.hallRepository = hallRepository;
        this.theatreService = theatreService;
    }

    // CREATE
    @Transactional
    public void save(String name, String theatreId, int capacity) {
        String id = UUID.randomUUID().toString();

        // ensure theatre exists
        Theatre theatre = theatreService.findById(theatreId)
                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + theatreId + " not found."));

        Hall hall = new Hall(id, name, theatreId, capacity);
        hallRepository.save(hall);
    }

    // UPDATE BASIC INFO
    @Transactional
    public Hall update(String id, String name, String theatreId, int capacity) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + id + " not found."));

        // optional: validate theatre exists
        theatreService.findById(theatreId)
                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + theatreId + " not found."));

        hall.setName(name);
        hall.setTheatreId(theatreId);
        hall.setCapacity(capacity);

        return hallRepository.save(hall);
    }

    @Transactional
    public void delete(String hallId) {
        hallRepository.deleteById(hallId);
    }

    public List<Theatre> getAvailableTheatres() {
        return theatreService.getAll();
    }

    @Transactional
    public void addSeat(String hallId, Seat seat) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        hall.addSeat(seat);
        hallRepository.save(hall);
    }

    @Transactional
    public void removeSeat(String hallId, String seatId) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        boolean removed = hall.removeSeat(seatId);

        if (removed) {
            hallRepository.save(hall);
        }
    }

    @Transactional
    public void updateSeat(String hallId, String seatId, Seat updatedSeat) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        boolean updated = hall.updateSeat(seatId, updatedSeat);

        if (updated) {
            hallRepository.save(hall);
        }
    }


    @Transactional
    public void addScreening(String hallId, Screening screening) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        hall.addScreening(screening);
        hallRepository.save(hall);
    }

    @Transactional
    public void removeScreening(String hallId, String screeningId) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        boolean removed = hall.removeScreening(screeningId);

        if (removed) {
            hallRepository.save(hall);
        }
    }

    @Transactional
    public void updateScreening(String hallId, String screeningId, Screening updatedScreening) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        boolean updated = hall.updateScreening(screeningId, updatedScreening);

        if (updated) {
            hallRepository.save(hall);
        }
    }

    @Transactional
    public Optional<Hall> findById(String id) {
        return hallRepository.findById(id);
    }

    public List<Hall> findAll() {
        return hallRepository.findAll();  // JPA method
    }
}
