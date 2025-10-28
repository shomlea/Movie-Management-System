package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.Screening;

import java.util.ArrayList;
import java.util.List;

public class ScreeningRepository {
    private List<Screening> screenings;

    public ScreeningRepository() {
        this.screenings = new ArrayList<>();
    }

    public List<Screening> getAllScreenings() {
        return screenings;
    }

    public Screening getScreeningById(String screeningId) {
        for (Screening screening : screenings) {
            if (screening.getId().equals(screeningId)) {
                return screening;
            }
        }
        return null;
    }

    public void addScreening(Screening screening) {
        screenings.add(screening);
    }
}
