package com.example.movie_management_system.model;

import jakarta.persistence.*;


import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String title;
    private int durationMin;
    private String genre; // added attribute
    @OneToMany(mappedBy = "movieId", cascade = CascadeType.ALL)
    private List<Screening> screenings;
    public Movie(){}

    public Movie(String id, String title, int durationMin, String genre) {
        this.id = id;
        this.title = title;
        this.durationMin = durationMin;
        this.genre = genre;
        this.screenings = new ArrayList<>();

    }

    public String getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getGenre(){return genre;  }
    public int getDurationMin(){
        return durationMin;
    }
    public List<Screening> getScreenings(){
        return screenings;
    }
    public void addScreening(Screening screening){
        this.screenings.add(screening);
    }
}