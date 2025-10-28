package com.example.movie_management_system.model;

import java.util.ArrayList;
import java.util.List;

public class Screening {
    private String id;
    private String hallId;
    private String MovieId;
    private String date;
    private List<Ticket> tickets;
    private List<StaffAssignment> assignments;

    public Screening(String id, String hallId, String movieId, String date) {
        this.id = id;
        this.hallId = hallId;
        this.MovieId = movieId;
        this.date = date;
        this.tickets = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public String getId() {
        return id;
    }
    public String getHallId() {
        return hallId;
    }
    public String getMovieId() {
        return MovieId;
    }
    public String getDate() {
        return date;
    }
    public List<Ticket> getTickets() {
        return tickets;
    }
    public List<StaffAssignment> getAssignments() {
        return assignments;
    }


    public void addTicket(Ticket ticket){
        tickets.add(ticket);
    }
    public void addAssignment(StaffAssignment assignment){
        assignments.add(assignment);
    }
}
