package com.example.movie_management_system.dto;

public class CustomerFilterDto {

    private String nameQuery;

    public String getNameQuery() {
        return nameQuery;
    }

    public void setNameQuery(String nameQuery) {
        this.nameQuery = nameQuery;
    }

    public boolean isEmpty() {
        return nameQuery == null;
    }
}