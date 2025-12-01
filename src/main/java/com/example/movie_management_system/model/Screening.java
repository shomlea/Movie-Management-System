package com.example.movie_management_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Screening {
    @Id
    private String id;

    @Column(nullable = false)
    private String hallId;

    @Column(nullable = false)
    private String movieId;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    private LocalDate date;

    @OneToMany(mappedBy = "screeningId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();
    @OneToMany(mappedBy = "screeningId", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StaffAssignment> assignments = new ArrayList<>();


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

    public void setId(String id) {
        this.id = id;
    }
    public void setHallId(String hallId) {
        this.hallId = hallId;
    }
    public void setMovieId(String movieId) {
        this.movieId = movieId;
    }
    public void setDate(String date) {
        this.date = LocalDate.parse(date);
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
    public List<StaffAssignment> getAssignments() {
        return assignments;
    }


    public void addTicket(Ticket ticket){
        this.tickets.add(ticket);
    }

    public boolean removeTicket(String ticketId){
        return this.tickets.removeIf(ticket -> ticket.getId().equals(ticketId));
    }

    public boolean updateTicket(String id, Ticket ticket){
        int index = -1;
        for (int i = 0; i < tickets.size(); i++) {
            if (id.equals(tickets.get(i).getId())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            tickets.set(index, ticket);
            return true;
        }
        return false;
    }
    public void addAssignment(StaffAssignment assignment){
        this.assignments.add(assignment);
    }

    public boolean removeAssignment(String assignmentId){
        return this.assignments.removeIf(assignment -> assignment.getId().equals(assignmentId));
    }

    public boolean updateAssignment(String id, StaffAssignment assignment){
        int index = -1;
        for (int i = 0; i < assignments.size(); i++) {
            if (id.equals(assignments.get(i).getId())) {
                index = i;
                break;
            }
        }

        if (index != -1) {
            assignments.set(index, assignment);
            return true;
        }
        return false;
    }
}
