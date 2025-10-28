package com.example.movie_management_system.model;

public class Seat {
    private String id;
    private String hallId;
    private String row; // "A,B,C,.."
    private String column; // "1,2,3.."

    public Seat(String id, String hallId, String row, String Column) {
        this.id = id;
        this.hallId = hallId;
        this.row = row;
        this.column = Column;
    }

    public String getId() {
        return id;
    }
    public String getHallId() {
        return hallId;
    }
    public String getRow() {
        return row;
    }
    public String getColumn() {
        return column;
    }
}
