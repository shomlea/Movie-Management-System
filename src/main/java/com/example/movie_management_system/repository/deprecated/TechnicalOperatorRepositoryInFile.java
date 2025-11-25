package com.example.movie_management_system.repository.deprecated;

import com.example.movie_management_system.model.TechnicalOperator;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class TechnicalOperatorRepositoryInFile extends  BaseRepositoryInFile<TechnicalOperator, String>{
    public TechnicalOperatorRepositoryInFile() {
        super(TechnicalOperator::getId, "data/technical_operators.json", TechnicalOperator.class);
    }
}
