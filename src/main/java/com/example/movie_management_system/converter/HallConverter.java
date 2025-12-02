package com.example.movie_management_system.converter;

import com.example.movie_management_system.model.Hall;
import com.example.movie_management_system.service.HallService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

@Component
public class HallConverter implements Converter<Long, Hall> {

    private final HallService hallService;

    public HallConverter(HallService hallService) {
        this.hallService = hallService;
    }

    @Override
    public Hall convert(Long id) {
        if (id == null) return null;

        return hallService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Hall with ID " + id + " not found."));
    }
}