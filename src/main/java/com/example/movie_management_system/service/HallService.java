package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.repository.HallRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Hall save(Hall hall) {
        Theatre theatre = theatreService.findById(hall.getTheatre().getId())
                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + hall.getTheatre().getId() + " not found."));

        Optional<Hall> foundHall = hallRepository.findByName(hall.getName());
        if(foundHall.isPresent()) {
            throw new DataIntegrityViolationException("There is already a Hall with that name");
        }

        hall.setTheatre(theatre);
        return hallRepository.save(hall);
    }

    @Transactional
    public Hall update(Long id, Hall updatedHall) {
        Hall existingHall = hallRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + id + " not found."));

        Long newTheatreId = updatedHall.getTheatre().getId();

        Theatre newTheatre = theatreService.findById(newTheatreId)
                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + newTheatreId + " not found."));

        Optional<Hall> foundHall = hallRepository.findByName(updatedHall.getName());
        if(foundHall.isPresent() && !foundHall.get().getId().equals(existingHall.getId())) {
            throw new DataIntegrityViolationException("There is already a Hall with that name");
        }

        existingHall.setName(updatedHall.getName());
        existingHall.setCapacity(updatedHall.getCapacity());
        existingHall.setTheatre(newTheatre);

        return hallRepository.save(existingHall);
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
