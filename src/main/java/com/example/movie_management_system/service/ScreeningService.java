package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.repository.ScreeningRepositoryInMemory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScreeningService {
    private ScreeningRepositoryInMemory screeningRepository;

    public ScreeningService(ScreeningRepositoryInMemory screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    public void add(String hallId, String movieId, String date) {
        String id = UUID.randomUUID().toString();
        Screening screening = new Screening(id, hallId, movieId, date);
        screeningRepository.add(screening);
    }

    public void remove(String id) {
        screeningRepository.remove(id);
    }

    public Optional<Screening> findById(String id) {
        return screeningRepository.findById(id);
    }

    public List<Screening> getAll() {
        return screeningRepository.getAll();
    }
}
