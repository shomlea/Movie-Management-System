package com.example.movie_management_system.service;


import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.repository.TheatreRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
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
    public Theatre save(Theatre theatre) {
        Optional<Theatre> foundTheatre = theatreRepository.findByName(theatre.getName());
        if(foundTheatre.isPresent())
            throw new DataIntegrityViolationException("There is already a Theatre with that name");
        return theatreRepository.save(theatre);
    }
    @Transactional
    public Theatre update(Long id, Theatre updatedTheatre) {
        Theatre existingTheatre = theatreRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + id + " not found."));

        Optional<Theatre> foundTheatre = theatreRepository.findByName(updatedTheatre.getName());
        if (foundTheatre.isPresent() && !foundTheatre.get().getId().equals(existingTheatre.getId())) {
            throw new DataIntegrityViolationException("There is already a Theatre with that name.");
        }

        existingTheatre.setName(updatedTheatre.getName());
        existingTheatre.setCity(updatedTheatre.getCity());
        existingTheatre.setParkingCapacity(updatedTheatre.getParkingCapacity());

        return theatreRepository.save(existingTheatre);
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
