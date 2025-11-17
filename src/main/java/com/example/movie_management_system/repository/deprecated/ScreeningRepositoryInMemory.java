package com.example.movie_management_system.repository.deprecated;

import com.example.movie_management_system.model.Screening;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class ScreeningRepositoryInMemory extends BaseRepositoryInMemory<Screening, String> {
    public ScreeningRepositoryInMemory() {
        super(Screening::getId);
    }
}
