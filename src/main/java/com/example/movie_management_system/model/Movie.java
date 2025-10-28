package com.example.movie_management_system.model;

import java.util.ArrayList;
import java.util.List;

public class Movie {
    private String id;
    private String title;
    private int durationMin;
    private List<Screening> screenings;

    public Movie(String id, String title, int durationMin){
        this.id = id;
        this.title = title;
        this.durationMin = durationMin;
        this.screenings = new ArrayList<>();
    }

    public String getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
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