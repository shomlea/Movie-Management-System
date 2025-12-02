package com.example.movie_management_system.converter;

import com.example.movie_management_system.model.Screening;
import com.example.movie_management_system.service.ScreeningService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

@Component
public class ScreeningConverter implements Converter<Long, Screening> {

    private final ScreeningService screeningService;

    public ScreeningConverter(ScreeningService screeningService) {
        this.screeningService = screeningService;
    }

    @Override
    public Screening convert(Long id) {
        if (id == null) return null;

        return screeningService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Screening with ID " + id + " not found."));
    }
}