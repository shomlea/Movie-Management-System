package com.example.movie_management_system.repository.deprecated;

import com.example.movie_management_system.model.Theatre;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class TheatreRepositoryInFile extends BaseRepositoryInFile<Theatre, String>{
    public TheatreRepositoryInFile() {
        super(Theatre::getId, "data/theatres.json", Theatre.class);
    }
}
