package com.example.movie_management_system.model;

import java.util.ArrayList;
import java.util.List;

public class Theatre {
    private String id;
    private String name;
    private String city;
    private List<Hall> halls;

    public Theatre(String id, String name, String city) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.halls = new ArrayList<>();
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCity() {
        return city;
    }
    public List<Hall> getHalls() {
        return halls;
    }

    public void addHall(Hall hall) {
        this.halls.add(hall);
    }
}