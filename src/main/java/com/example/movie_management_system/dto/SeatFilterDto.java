package com.example.movie_management_system.dto;

public class SeatFilterDto {

    private String hallNameQuery;


    public String getHallNameQuery() {
        return hallNameQuery;
    }

    public void setHallNameQuery(String hallNameQuery) {
        this.hallNameQuery = hallNameQuery;
    }

    public boolean isEmpty() {
        return (hallNameQuery == null || hallNameQuery.trim().isEmpty());
    }
}