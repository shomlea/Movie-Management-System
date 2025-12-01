package com.example.movie_management_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;


import java.util.ArrayList;
import java.util.List;

@Entity
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Movie title is required.")
    private String title;

    @Column(nullable = false)
    @Positive(message = "Duration must be a positive number.")
    private int durationMin;

    @Column(nullable = false)
    @NotBlank(message = "Movie genre is required.")
    private String genre;

    @OneToMany(mappedBy = "movie", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Screening> screenings = new ArrayList<>();
    public Movie(){}

    public Movie(String title, int durationMin, String genre) {
        this.title = title;
        this.durationMin = durationMin;
        this.genre = genre;

    }

    public Long getId(){
        return id;
    }
    public String getTitle(){
        return title;
    }
    public String getGenre(){return genre;  }
    public int getDurationMin(){
        return durationMin;
    }

    public void setId(Long id){
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
//    public void addScreening(Screening screening){
//        this.screenings.add(screening);
//    }
//    public boolean removeScreening(Long screeningId){
//        return this.screenings.removeIf(screening -> screening.getId().equals(screeningId));
//    }
//    public boolean updateScreening(Long id, Screening screening){
//        int index = -1;
//        for (int i = 0; i < screenings.size(); i++) {
//            if (id.equals(screenings.get(i).getId())) {
//                index = i;
//                break;
//            }
//        }
//        if (index != -1) {
//            screenings.set(index, screening);
//            return true;
//        }
//        return false;
//    }
}