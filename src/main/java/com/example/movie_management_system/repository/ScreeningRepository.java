package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.Screening;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public interface ScreeningRepository extends JpaRepository<Screening,Long>, JpaSpecificationExecutor<Screening> {
    Optional<Screening> findByHallIdAndMovieIdAndDate(Long hallId, Long movieId, LocalDate date);
}
