package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.TechnicalOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TechnicalOperatorRepository extends JpaRepository<TechnicalOperator,Long> {
    Optional<TechnicalOperator> findByName(String name);
}
