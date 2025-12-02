package com.example.movie_management_system.converter;

import com.example.movie_management_system.model.Theatre;
import com.example.movie_management_system.service.TheatreService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

@Component
public class TheatreConverter implements Converter<Long, Theatre> {

    private final TheatreService theatreService;

    public TheatreConverter(TheatreService theatreService) {
        this.theatreService = theatreService;
    }

    @Override
    public Theatre convert(Long id) {
        if (id == null) return null; // Handle null input gracefully

        return theatreService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Theatre with ID " + id + " not found."));
    }
}