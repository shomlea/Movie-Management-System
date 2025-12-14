package com.example.movie_management_system.dto;

public class HallFilterDto {

    private String nameQuery;
    private String theatreNameQuery;

    private Integer minCapacity;
    private Integer maxCapacity;

    public String getNameQuery() {
        return nameQuery;
    }

    public void setNameQuery(String nameQuery) {
        this.nameQuery = nameQuery;
    }

    public String getTheatreNameQuery() {
        return theatreNameQuery;
    }

    public void setTheatreNameQuery(String theatreNameQuery) {
        this.theatreNameQuery = theatreNameQuery;
    }

    public Integer getMinCapacity() {
        return minCapacity;
    }

    public void setMinCapacity(Integer minCapacity) {
        this.minCapacity = minCapacity;
    }

    public Integer getMaxCapacity() {
        return maxCapacity;
    }

    public void setMaxCapacity(Integer maxCapacity) {
        this.maxCapacity = maxCapacity;
    }

    public boolean isEmpty() {
        return (nameQuery == null || nameQuery.trim().isEmpty()) &&
                (theatreNameQuery == null || theatreNameQuery.trim().isEmpty()) &&
                minCapacity == null &&
                maxCapacity == null;
    }
}