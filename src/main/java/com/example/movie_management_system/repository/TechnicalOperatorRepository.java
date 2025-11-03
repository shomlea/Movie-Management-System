package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.TechnicalOperator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TechnicalOperatorRepository extends JpaRepository<TechnicalOperator, String> {
}
