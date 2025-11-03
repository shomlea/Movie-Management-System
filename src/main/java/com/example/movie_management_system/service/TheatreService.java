package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.repository.TheatreRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheatreService {
    private final TheatreRepository theatreRepository;
    public TheatreService(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    public Theatre addTheatre(Theatre theatre) {
        return theatreRepository.save(theatre);
    }

    public void removeTheatre(Theatre theatre) {
        theatreRepository.delete(theatre);
    }

    public Optional<Theatre> findById(String id) {
        return theatreRepository.findById(id);
    }

    public List<Theatre> getAllTheatres() {
        return theatreRepository.findAll();
    }

}
