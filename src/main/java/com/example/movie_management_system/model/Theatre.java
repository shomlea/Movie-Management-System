package com.example.movie_management_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;
@Entity
public class Theatre {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Theatre name is required")
    private String name;

    @Column(nullable = false)
    @NotBlank(message = "Theatre city is required")
    private String city;

    @Column(nullable = false)
    @Positive(message = "Parking capacity should be a positive number")
    private int parkingCapacity;

    @OneToMany(mappedBy = "theatre", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Hall> halls = new ArrayList<>();

    public Theatre(String name, String city, int parkingCapacity) {
        this.name = name;
        this.city = city;
        this.parkingCapacity = parkingCapacity;
    }

    public Theatre() {

    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getCity() {
        return city;
    }
    public int getParkingCapacity() {return parkingCapacity;}

    public void setId(Long id) {
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

//    public void addHall(Hall hall) {
//        this.halls.add(hall);
//    }
//
//    public boolean removeHall(Long hallId) {
//        return this.halls.removeIf(hall -> hall.getId().equals(hallId));
//    }
//
//    public boolean updateHall(Long id, Hall hall) {
//        int index = -1;
//        for (int i = 0; i < halls.size(); i++) {
//            if (id.equals(halls.get(i).getId())) {
//                index = i;
//                break;
//            }
//        }
//
//        if (index != -1) {
//            halls.set(index, hall);
//            return true;
//        }
//        return false;
//    }
}