package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.repository.HallRepositoryInFile;
import com.example.movie_management_system.repository.TheatreRepositoryInFile;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
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
        if(theatreService.findById(theatreId).isPresent()){
            hallRepository.add(hall);
        }

    }

    public boolean update(String id, String name,  String theatreId, int capacity){
        Hall hall = new Hall(id, name, theatreId, capacity);
        return hallRepository.update(id, hall);
    }

    public void remove(String hallId){
        hallRepository.remove(hallId);
    }

    public List<Theatre> getAvailableTheatres(){
        return theatreService.getAll();
    }

    @Transactional
    public void addSeat(String hallId, Seat seat) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        hall.addSeat(seat);

        hallRepository.add(hall);
    }

    @Transactional
    public boolean removeSeat(String hallId, String seatId) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        boolean removed = hall.removeSeat(seatId);

        if (removed) {
            hallRepository.add(hall);
        }
        return removed;
    }

    @Transactional
    public boolean updateSeat(String hallId, String seatId, Seat updatedSeat) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        boolean updated = hall.updateSeat(seatId, updatedSeat);

        if (updated) {
            hallRepository.update(hallId, hall);
        }
        return updated;
    }


    @Transactional
    public void addScreening(String hallId, Screening screening) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        hall.addScreening(screening);

        hallRepository.update(hallId, hall);
    }

    @Transactional
    public boolean removeScreening(String hallId, String screeningId) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        boolean removed = hall.removeScreening(screeningId);

        if (removed) {
            hallRepository.update(hallId, hall);
        }
        return removed;
    }

    @Transactional
    public boolean updateScreening(String hallId, String screeningId, Screening updatedScreening) {
        Hall hall = hallRepository.findById(hallId)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + hallId + " not found."));

        boolean updated = hall.updateScreening(screeningId, updatedScreening);

        if (updated) {
            hallRepository.update(hallId, hall);
        }
        return updated;
    }

    public Optional<Hall> findById(String id) {
        return hallRepository.findById(id);
    }

    public List<Hall> getAll() {
        return hallRepository.getAll();
    }
}
