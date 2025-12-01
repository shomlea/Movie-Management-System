package com.example.movie_management_system.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
@Entity
public class Theatre {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private int parkingCapacity;

    @OneToMany(mappedBy = "theatreId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hall> halls = new ArrayList<>();

    public Theatre(String id, String name, String city, int parkingCapacity) {
        this.id = id;
        this.name = name;
        this.city = city;
        this.parkingCapacity = parkingCapacity;
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

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setCity(String city) {
        this.city = city;
    }
    public void setParkingCapacity(int parkingCapacity) {
        this.parkingCapacity = parkingCapacity;
    }

    public List<Hall> getHalls() {
        return halls;
    }

    public void addHall(Hall hall) {
        this.halls.add(hall);
    }

    public boolean removeHall(String hallId) {
        return this.halls.removeIf(hall -> hall.getId().equals(hallId));
    }

    public boolean updateHall(String id, Hall hall) {
        int index = -1;
        for (int i = 0; i < halls.size(); i++) {
            if (id.equals(halls.get(i).getId())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            halls.set(index, hall);
            return true;
        }
        return false;
    }
}