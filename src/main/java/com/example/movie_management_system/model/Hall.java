package com.example.movie_management_system.model;

import java.util.List;

public class Hall {
    private String id;
    private String name;
    private String theatreId;
    private int capacity;
    private List<Seat> seats;
    private List<Screening> screenings;

    public Hall(String id, String name, String theatreId, int capacity) {
        this.id = id;
        this.name = name;
        this.theatreId = theatreId;
        this.capacity = capacity;
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
    public List<Seat> getSeats() {
        return seats;
    }
    public List<Screening> getScreenings() {
        return screenings;
    }

    public void addSeat(Seat seat) {
        this.seats.add(seat);
    }
    public void addScreening(Screening screening) {
        this.screenings.add(screening);
    }

}