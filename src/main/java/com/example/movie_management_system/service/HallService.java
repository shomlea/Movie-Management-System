package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class HallService {
    private final HallRepository hallRepository;

    public HallService(HallRepository hallRepository) {
        this.hallRepository = hallRepository;
    }

    public Hall addHall(String name, String theatreId, int capacity){
        String id = UUID.randomUUID().toString();
        Hall hall = new Hall(id, name, theatreId, capacity);
        return hallRepository.save(hall);
    }

    public void removeHall(String hallId){
        hallRepository.deleteById(hallId);
    }

    public Optional<Hall> findById(String id) {
        return hallRepository.findById(id);
    }

    public List<Hall> getAllHalls() {
        return hallRepository.findAll();
    }
}
