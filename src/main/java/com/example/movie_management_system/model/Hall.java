package com.example.movie_management_system.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Hall {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String theatreId;

    @Column(nullable = false)
    private int capacity;

    @OneToMany(mappedBy = "hallId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Seat> seats = new ArrayList<>();

    @OneToMany(mappedBy = "hallId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Screening> screenings = new ArrayList<>();

    public Hall(String id, String name, String theatreId, int capacity) {
        this.id = id;
        this.name = name;
        this.theatreId = theatreId;
        this.capacity = capacity;
    }
    public Hall() {
        seats = new ArrayList<>();
        screenings = new ArrayList<>();
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getTheatreId() {
        return theatreId;
    }
    public int getCapacity() {
        return capacity;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setTheatreId(String theatreId) {
        this.theatreId = theatreId;
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

    public void addSeat(Seat seat) {
        this.seats.add(seat);
    }

    public boolean removeSeat(String seatId) {
        return this.seats.removeIf(seat -> seat.getId().equals(seatId));
    }

    public boolean updateSeat(String id, Seat seat) {
        int index = -1;
        for (int i = 0; i < seats.size(); i++) {
            if (id.equals(seats.get(i).getId())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            seats.set(index, seat);
            return true;
        }
        return false;
    }



    public void addScreening(Screening screening) {
        this.screenings.add(screening);
    }

    public boolean removeScreening(String screeningId) {

        return this.screenings.removeIf(screening -> screening.getId().equals(screeningId));
    }

    public boolean updateScreening(String id, Screening screening) {
        int index = -1;
        for (int i = 0; i < screenings.size(); i++) {
            if (id.equals(screenings.get(i).getId())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            screenings.set(index, screening);
            return true;
        }
        return false;
    }

}