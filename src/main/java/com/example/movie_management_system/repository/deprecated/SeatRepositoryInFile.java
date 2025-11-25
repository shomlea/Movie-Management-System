package com.example.movie_management_system.repository.deprecated;

import com.example.movie_management_system.model.Seat;

import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class SeatRepositoryInFile extends BaseRepositoryInFile<Seat, String>{
    public SeatRepositoryInFile() {
        super(Seat::getId, "data/seats.json", Seat.class);
    }
}
