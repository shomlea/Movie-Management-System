package com.example.movie_management_system.dto;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ScreeningFilterDto {

    private String movieTitleQuery;
    private String hallNameQuery;
    private String theatreNameQuery;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateAfter;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate dateBefore;

    public String getMovieTitleQuery() {
        return movieTitleQuery;
    }

    public void setMovieTitleQuery(String movieTitleQuery) {
        this.movieTitleQuery = movieTitleQuery;
    }

    public String getHallNameQuery() {
        return hallNameQuery;
    }

    public void setHallNameQuery(String hallNameQuery) {
        this.hallNameQuery = hallNameQuery;
    }

    public String getTheatreNameQuery() {
        return theatreNameQuery;
    }

    public void setTheatreNameQuery(String theatreNameQuery) {
        this.theatreNameQuery = theatreNameQuery;
    }

    public LocalDate getDateAfter() {
        return dateAfter;
    }

    public void setDateAfter(LocalDate dateAfter) {
        this.dateAfter = dateAfter;
    }

    public LocalDate getDateBefore() {
        return dateBefore;
    }

    public void setDateBefore(LocalDate dateBefore) {
        this.dateBefore = dateBefore;
    }

    public boolean isEmpty() {
        return (movieTitleQuery == null || movieTitleQuery.trim().isEmpty()) &&
                (hallNameQuery == null || hallNameQuery.trim().isEmpty()) &&
                (theatreNameQuery == null || theatreNameQuery.trim().isEmpty()) &&
                dateAfter == null &&
                dateBefore == null;
    }
}