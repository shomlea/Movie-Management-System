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

    @Transactional
    public Hall save(String name, Long theatreId, int capacity) {

        Theatre theatre = theatreService.findById(theatreId)
                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + theatreId + " not found."));

        Hall hall = new Hall(name, theatre, capacity);
        return hallRepository.save(hall);
    }

    @Transactional
    public Hall update(Long id, String name, Long theatreId, int capacity) {
        Hall hall = hallRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + id + " not found."));

        Theatre theatre = theatreService.findById(theatreId)
                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + theatreId + " not found."));

        hall.setName(name);
        hall.setTheatre(theatre);
        hall.setCapacity(capacity);

        return hallRepository.save(hall);
    }

    @Transactional
    public void delete(Long id) {
        if (!hallRepository.existsById(id)) {
            throw new NoSuchElementException("Hall with ID " + id + " not found.");
        }
        hallRepository.deleteById(id);
    }

    public List<Theatre> getAvailableTheatres() {
        return theatreService.findAll();
    }


    public Optional<Hall> findById(Long id) {
        return hallRepository.findById(id);
    }

    public List<Hall> findAll() {
        return hallRepository.findAll();  // JPA method
    }
}
