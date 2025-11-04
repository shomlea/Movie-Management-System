package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.repository.TheatreRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TheatreService {
    private final TheatreRepository theatreRepository;
    public TheatreService(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }

    public Theatre add(String name, String city, int parkingCapacity) {
        String id = UUID.randomUUID().toString();
        Theatre theatre = new Theatre(id, name, city, parkingCapacity);
        return theatreRepository.add(theatre);
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
