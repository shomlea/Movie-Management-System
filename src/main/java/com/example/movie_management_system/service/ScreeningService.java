package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.repository.ScreeningRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ScreeningService {
    private ScreeningRepository screeningRepository;

    public ScreeningService(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    public Screening addScreening(String hallId, String movieId, String date) {
        String id = UUID.randomUUID().toString();
        Screening screening = new Screening(id, hallId, movieId, date);
        return screeningRepository.save(screening);
    }

    public void removeScreening(String id) {
        screeningRepository.deleteById(id);
    }

    public Optional<Screening> findById(String id) {
        return screeningRepository.findById(id);
    }

    public List<Screening> getAllScreenings() {
        return screeningRepository.findAll();
    }
}
