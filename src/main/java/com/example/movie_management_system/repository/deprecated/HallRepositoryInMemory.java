package com.example.movie_management_system.repository.deprecated;

import com.example.movie_management_system.model.Hall;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class HallRepositoryInMemory extends BaseRepositoryInMemory<Hall, String> {
    public HallRepositoryInMemory() {
        super(Hall::getId);
    }
}
