package com.example.movie_management_system.repository.deprecated;

import com.example.movie_management_system.model.Hall;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary

public class HallRepositoryInFile extends BaseRepositoryInFile<Hall, String>{

    public HallRepositoryInFile() {
        super(Hall::getId, "data/halls.json", Hall.class);
    }
}
