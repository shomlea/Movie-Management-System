package com.example.movie_management_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Seat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "hall_id", nullable = false)
    private Hall hall;

    @Column(nullable = false)
    @NotBlank(message = "Seat row is required.")
    private String seatRow; // "A,B,C,.."

    @Column(nullable = false)
    @NotBlank(message = "Seat column is required.")
    private String seatColumn; // "1,2,3.."

    public Seat(Hall hall, String seatRow, String seatColumn) {
        this.hall = hall;
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
    }

    public Seat() {

    }

    public Long getId() {
        return id;
    }
    public Hall getHall() {return hall;}
    public String getSeatRow() {
        return seatRow;
    }
    public String getSeatColumn() {
        return seatColumn;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setHall(Hall hall) {
        this.hall = hall;
    }
    public void setSeatRow(String seatRow) {
        this.seatRow = seatRow;
    }
    public void setSeatColumn(String seatColumn) {
        this.seatColumn = seatColumn;
    }
}
