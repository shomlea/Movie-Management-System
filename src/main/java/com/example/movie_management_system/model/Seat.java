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
    public String getSeatRow() {
        return seatRow;
    }
    public String getSeatColumn() {
        return seatColumn;
    }

    public void setId(String id) {
        this.id = id;
    }
    public void setHallId(String hallId) {
        this.hallId = hallId;
    }
    public void setSeatRow(String seatRow) {
        this.seatRow = seatRow;
    }
    public void setSeatColumn(String seatColumn) {
        this.seatColumn = seatColumn;
    }
}
