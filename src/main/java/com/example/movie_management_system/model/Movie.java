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
    private List<Screening> screenings = new ArrayList<>();
    public Movie(){}

    public Movie(String id, String title, int durationMin, String genre) {
        this.id = id;
        this.title = title;
        this.durationMin = durationMin;
        this.genre = genre;

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

    public void setId(String id){
        this.id = id;
    }
    public void setTitle(String title){
        this.title = title;
    }
    public void setGenre(String genre){
        this.genre = genre;
    }
    public void setDurationMin(int durationMin){
        this.durationMin = durationMin;
    }

    public List<Screening> getScreenings(){
        return screenings;
    }
    public void addScreening(Screening screening){
        this.screenings.add(screening);
    }
    public boolean removeScreening(String screeningId){
        return this.screenings.removeIf(screening -> screening.getId().equals(screeningId));
    }
    public boolean updateScreening(String id, Screening screening){
        int index = -1;
        for (int i = 0; i < screenings.size(); i++) {
            if (id.equals(screenings.get(i).getId())) {
                index = i;
                break;
            }
        }
        if (index != -1) {
            screenings.set(index, screening);
            return true;
        }
        return false;
    }
}