package com.example.movie_management_system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Hall {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Hall name is required.")
    private String name;

    @ManyToOne
    @JoinColumn(name = "theatre_id", nullable = false)
    private Theatre theatre;


    @Column(nullable = false)
    @Positive(message = "Capacity must be a positive number.")
    private int capacity;

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "hall", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Screening> screenings = new ArrayList<>();

    public Hall(String name, Theatre theatre, int capacity) {
        this.name = name;
        this.theatre = theatre;
        this.capacity = capacity;
    }
    public Hall() {
        seats = new ArrayList<>();
        screenings = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public Theatre getTheatre() {return theatre;}
    public int getCapacity() {
        return capacity;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTheatre(Theatre theatre) {
        this.theatre = theatre;
    }
    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Seat> getSeats() {
        return seats;
    }
    public List<Screening> getScreenings() {
        return screenings;
    }

//    public void addSeat(Seat seat) {
//        this.seats.add(seat);
//    }
//
//    public boolean removeSeat(Long seatId) {
//        return this.seats.removeIf(seat -> seat.getId().equals(seatId));
//    }
//
//    public boolean updateSeat(Long id, Seat seat) {
//        int index = -1;
//        for (int i = 0; i < seats.size(); i++) {
//            if (id.equals(seats.get(i).getId())) {
//                index = i;
//                break;
//            }
//        }
//
//        if (index != -1) {
//            seats.set(index, seat);
//            return true;
//        }
//        return false;
//    }



//    public void addScreening(Screening screening) {
//        this.screenings.add(screening);
//    }
//
//    public boolean removeScreening(String screeningId) {
//
//        return this.screenings.removeIf(screening -> screening.getId().equals(screeningId));
//    }
//
//    public boolean updateScreening(String id, Screening screening) {
//        int index = -1;
//        for (int i = 0; i < screenings.size(); i++) {
//            if (id.equals(screenings.get(i).getId())) {
//                index = i;
//                break;
//            }
//        }
//
//        if (index != -1) {
//            screenings.set(index, screening);
//            return true;
//        }
//        return false;
//    }

}