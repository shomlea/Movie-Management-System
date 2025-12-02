package com.example.movie_management_system.converter;

import com.example.movie_management_system.model.Seat;
import com.example.movie_management_system.service.SeatService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

@Component
public class SeatConverter implements Converter<Long, Seat> {

    private final SeatService seatService;

    public SeatConverter(SeatService seatService) {
        this.seatService = seatService;
    }

    @Override
    public Seat convert(Long id) {
        if (id == null) return null;

        return seatService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Seat with ID " + id + " not found."));
    }
}