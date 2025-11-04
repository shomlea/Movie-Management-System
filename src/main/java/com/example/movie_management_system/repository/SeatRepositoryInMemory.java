package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.Seat;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class SeatRepositoryInMemory extends BaseRepositoryInMemory<Seat, String> {
    public SeatRepositoryInMemory() {
        super(Seat::getId);
    }
}
