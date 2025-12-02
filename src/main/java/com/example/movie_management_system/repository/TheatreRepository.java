package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre,Long> {
    Optional<Theatre> findByName(String name);
}
