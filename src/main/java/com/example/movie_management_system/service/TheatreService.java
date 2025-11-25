package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.repository.deprecated.TheatreRepositoryInFile;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TheatreService {
    private final TheatreRepositoryInFile theatreRepository;
    public TheatreService(TheatreRepositoryInFile theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    public void add(String name, String city, int parkingCapacity) {
        String id = UUID.randomUUID().toString();
        Theatre theatre = new Theatre(id, name, city, parkingCapacity);
        theatreRepository.add(theatre);
    }

    public boolean update(String id, String name, String city, int parkingCapacity) {
        Theatre theatre = new Theatre(id, name, city, parkingCapacity);
        return theatreRepository.update(id, theatre);
    }

    @Transactional
    public void addHall(String theatreId, Hall hall) {
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + theatreId + " not found."));

        theatre.addHall(hall);

        theatreRepository.update(theatreId, theatre);
    }

    @Transactional
    public boolean removeHall(String theatreId, String hallId) {
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + theatreId + " not found."));

        boolean removed = theatre.removeHall(hallId);

        if (removed) {
            theatreRepository.update(theatreId, theatre);
        }
        return removed;
    }

    @Transactional
    public boolean updateHall(String theatreId, String hallId, Hall updatedHall) {
        Theatre theatre = theatreRepository.findById(theatreId)
                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + theatreId + " not found."));

        boolean updated = theatre.updateHall(hallId, updatedHall);

        if (updated) {
            theatreRepository.update(theatreId, theatre);
        }
        return updated;
    }

    public void remove(String id) {
        theatreRepository.remove(id);
    }

    public Optional<Theatre> findById(String id) {
        return theatreRepository.findById(id);
    }

    public List<Theatre> getAll() {
        return theatreRepository.getAll();
    }

}
