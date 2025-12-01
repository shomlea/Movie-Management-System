package com.example.movie_management_system.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Screening {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @ManyToOne
    @JoinColumn(name = "movie_id", nullable = false)
    private Movie movie;

    @Column(nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
    @FutureOrPresent(message = "The screening date must be today or in the future.")
    private LocalDate date;

    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Ticket> tickets = new ArrayList<>();
    @OneToMany(mappedBy = "screening", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StaffAssignment> assignments = new ArrayList<>();


    public Screening(Hall hall, Movie movie, LocalDate date) {
        this.hall = hall;
        this.movie = movie;
        this.date = date;
        this.tickets = new ArrayList<>();
        this.assignments = new ArrayList<>();
    }

    public Screening() {

    }

    public Long getId() {
        return id;
    }
    public Hall getHall() {return hall;}
    public Movie getMovie() {return movie;}
    public LocalDate getDate() {
        return date;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setHall(Hall hall) {
        this.hall = hall;
    }
    public void setMovie(Movie movie) {
        this.movie = movie;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }

    public List<Ticket> getTickets() {
        return tickets;
    }
    public List<StaffAssignment> getAssignments() {
        return assignments;
    }


//    public void addTicket(Ticket ticket){
//        this.tickets.add(ticket);
//    }
//
//    public boolean removeTicket(Long ticketId){
//        return this.tickets.removeIf(ticket -> ticket.getId().equals(ticketId));
//    }
//
//    public boolean updateTicket(Long id, Ticket ticket){
//        int index = -1;
//        for (int i = 0; i < tickets.size(); i++) {
//            if (id.equals(tickets.get(i).getId())) {
//                index = i;
//                break;
//            }
//        }
//
//        if (index != -1) {
//            tickets.set(index, ticket);
//            return true;
//        }
//        return false;
//    }
//    public void addAssignment(StaffAssignment assignment){
//        this.assignments.add(assignment);
//    }
//
//    public boolean removeAssignment(Long assignmentId){
//        return this.assignments.removeIf(assignment -> assignment.getId().equals(assignmentId));
//    }
//
//    public boolean updateAssignment(Long id, StaffAssignment assignment){
//        int index = -1;
//        for (int i = 0; i < assignments.size(); i++) {
//            if (id.equals(assignments.get(i).getId())) {
//                index = i;
//                break;
//            }
//        }
//
//        if (index != -1) {
//            assignments.set(index, assignment);
//            return true;
//        }
//        return false;
//    }
}
