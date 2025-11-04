package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.TechnicalOperator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class TechnicalOperatorRepositoryInMemory extends BaseRepositoryInMemory<TechnicalOperator, String> {
    public TechnicalOperatorRepositoryInMemory() {
        super(TechnicalOperator::getId);
    }
}
