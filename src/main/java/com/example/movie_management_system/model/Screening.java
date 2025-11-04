package com.example.movie_management_system.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Screening {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String hallId;
    private String movieId;
    private LocalDate date;
    @OneToMany(mappedBy = "screeningId", cascade = CascadeType.ALL)
    private List<Ticket> tickets;
    @OneToMany(mappedBy = "screeningId", cascade = CascadeType.ALL)
    private List<StaffAssignment> assignments;


    public Screening(String id, String hallId, String movieId, String date) {
        this.id = id;
        this.hallId = hallId;
        this.movieId = movieId;
        this.date = LocalDate.parse(date); // date parsing
        this.tickets = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public Screening() {

    }

    public String getId() {
        return id;
    }
    public String getHallId() {
        return hallId;
    }
    public String getMovieId() {
        return movieId;
    }
    public LocalDate getDate() {
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
