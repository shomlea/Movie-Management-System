package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.repository.HallRepositoryInFile;
import com.example.movie_management_system.repository.TheatreRepositoryInFile;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HallService {
    private final HallRepositoryInFile hallRepository;
    private final TheatreService theatreService;

    public HallService(HallRepositoryInFile hallRepository, TheatreService theatreService) {
        this.hallRepository = hallRepository;
        this.theatreService = theatreService;
    }

    public void add(String name, String theatreId, int capacity){
        String id = UUID.randomUUID().toString();
        Hall hall = new Hall(id, name, theatreId, capacity);
        hallRepository.add(hall);
    }

    public boolean update(String id, String name,  String theatreId, int capacity){
        Hall hall = new Hall(id, name, theatreId, capacity);
        return hallRepository.update(id, hall);
    }

    public List<Theatre> getAvailableTheatres(){
        return theatreService.getAll();
    }

    public void remove(String hallId){
        hallRepository.remove(hallId);
    }

    public Optional<Hall> findById(String id) {
        return hallRepository.findById(id);
    }

    public List<Hall> getAll() {
        return hallRepository.getAll();
    }
}
