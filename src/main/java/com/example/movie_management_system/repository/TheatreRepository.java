package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.Theatre;

import java.util.ArrayList;
import java.util.List;

public class TheatreRepository {
    private List<Theatre> theatres;

    public TheatreRepository() {
        this.theatres = new ArrayList<>();
    }

    public List<Theatre> getAllTheatres() {
        return theatres;
    }

    public Theatre getTheatreById(String theatreId){
        for(Theatre theatre : theatres){
            if(theatre.getId().equals(theatreId)){
                return theatre;
            }
        }
        return null;
    }

    public void addTheatre(Theatre theatre){
        theatres.add(theatre);
    }
}