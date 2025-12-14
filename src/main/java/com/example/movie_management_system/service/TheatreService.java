package com.example.movie_management_system.service;


import com.example.movie_management_system.dto.TheatreFilterDto;
import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.repository.TheatreRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
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

    public Page<Theatre> findAll(TheatreFilterDto filter, Pageable pageable) {

        if (filter.isEmpty()) {
            return theatreRepository.findAll(pageable);
        }

        Specification<Theatre> spec = (root, query, cb) -> cb.conjunction();

        if (filter.getNameQuery() != null && !filter.getNameQuery().trim().isEmpty()) {
            final String namePattern = "%" + filter.getNameQuery().trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("name")), namePattern)
            );
        }

        if (filter.getCityQuery() != null && !filter.getCityQuery().trim().isEmpty()) {
            final String cityPattern = "%" + filter.getCityQuery().trim().toLowerCase() + "%";
            spec = spec.and((root, query, cb) ->
                    cb.like(cb.lower(root.get("city")), cityPattern)
            );
        }

        if (filter.getMinParkingCapacity() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.greaterThanOrEqualTo(root.get("parkingCapacity"), filter.getMinParkingCapacity())
            );
        }

        if (filter.getMaxParkingCapacity() != null) {
            spec = spec.and((root, query, cb) ->
                    cb.lessThanOrEqualTo(root.get("parkingCapacity"), filter.getMaxParkingCapacity())
            );
        }

        return theatreRepository.findAll(spec, pageable);
    }
}
