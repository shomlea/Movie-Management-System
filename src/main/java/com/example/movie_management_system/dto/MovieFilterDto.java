package com.example.movie_management_system.dto;

import java.util.Objects;

public class MovieFilterDto {

    private String titleQuery;

    private Integer minDurationMin;

    private Integer maxDurationMin;

    private String genreQuery;


    public String getTitleQuery() {
        return titleQuery;
    }

    public void setTitleQuery(String titleQuery) {
        this.titleQuery = titleQuery;
    }

    public Integer getMinDurationMin() {
        return minDurationMin;
    }

    public void setMinDurationMin(Integer minDurationMin) {
        this.minDurationMin = minDurationMin;
    }

    public Integer getMaxDurationMin() {
        return maxDurationMin;
    }

    public void setMaxDurationMin(Integer maxDurationMin) {
        this.maxDurationMin = maxDurationMin;
    }

    public String getGenreQuery() {
        return genreQuery;
    }

    public void setGenreQuery(String genreQuery) {
        this.genreQuery = genreQuery;
    }

    public boolean isEmpty() {
        return (titleQuery == null || titleQuery.trim().isEmpty()) &&
                minDurationMin == null &&
                maxDurationMin == null &&
                (genreQuery == null || genreQuery.trim().isEmpty());
    }
}