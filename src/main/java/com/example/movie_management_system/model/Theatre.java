package com.example.movie_management_system.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class Theatre {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private String city;
    private int parkingCapacity; // added attribute
    @OneToMany(mappedBy = "theatreId", cascade = CascadeType.ALL)
    private List<Hall> halls;

    public Theatre(String id, String name, String city, int parkingCapacity) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.parkingCapacity = parkingCapacity;
        this.halls = new ArrayList<>();
    }

    public Theatre() {

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
    public int getParkingCapacity() {return parkingCapacity;}
    public List<Hall> getHalls() {
        return halls;
    }

    public void addHall(Hall hall) {
        this.halls.add(hall);
    }
}