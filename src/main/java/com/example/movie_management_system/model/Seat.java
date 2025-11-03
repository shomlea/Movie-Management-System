package com.example.movie_management_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Seat {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String hallId;
    private String seatRow; // "A,B,C,.."
    private String seatColumn; // "1,2,3.."

    public Seat(String id, String hallId, String seatRow, String seatColumn) {
        this.id = id;
        this.hallId = hallId;
        this.seatRow = seatRow;
        this.seatColumn = seatColumn;
    }

    public Seat() {

    }

    public String getId() {
        return id;
    }
    public String getHallId() {
        return hallId;
    }
    public String getRow() {
        return seatRow;
    }
    public String getColumn() {
        return seatColumn;
    }
}
