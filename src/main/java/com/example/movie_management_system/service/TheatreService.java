package com.example.movie_management_system.service;


import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.repository.TheatreRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
public class TheatreService {
    private final TheatreRepository theatreRepository;
    public TheatreService(TheatreRepository theatreRepository) {
        this.theatreRepository = theatreRepository;
    }
    @Transactional
    public Theatre save(String name, String city, int parkingCapacity) {
        Theatre theatre = new Theatre(name, city, parkingCapacity);
        return theatreRepository.save(theatre);
    }
    @Transactional
    public Theatre update(Long id, String name, String city, int parkingCapacity) {
        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + id + " not found."));

        theatre.setName(name);
        theatre.setCity(city);
        theatre.setParkingCapacity(parkingCapacity);

        return theatreRepository.save(theatre);
    }

    @Transactional
    public void delete(Long id) {
        Theatre theatre = theatreRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + id + " not found."));
        theatreRepository.deleteById(id);
    }

    public Optional<Theatre> findById(Long id) {
        return theatreRepository.findById(id);
    }

    public List<Theatre> findAll() {
        return theatreRepository.findAll();
    }

    //    @Transactional
//    public void addHall(Long theatreId, Hall hall) {
//        Theatre theatre = theatreRepository.findById(theatreId)
//                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + theatreId + " not found."));
//
//        theatre.addHall(hall);
//
//        theatreRepository.save(theatre);
//    }
//
//    @Transactional
//    public boolean removeHall(String theatreId, String hallId) {
//        Theatre theatre = theatreRepository.findById(theatreId)
//                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + theatreId + " not found."));
//
//        boolean removed = theatre.removeHall(hallId);
//
//        if (removed) {
//            theatreRepository.save(theatre);
//        }
//        return removed;
//    }
//
//    @Transactional
//    public boolean updateHall(String theatreId, String hallId, Hall updatedHall) {
//        Theatre theatre = theatreRepository.findById(theatreId)
//                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + theatreId + " not found."));
//
//        boolean updated = theatre.updateHall(hallId, updatedHall);
//
//        if (updated) {
//            theatreRepository.save(theatre);
//        }
//        return updated;
//    }

}
