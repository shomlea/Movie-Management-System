package com.example.movie_management_system.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class StaffAssignment {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String screeningId;
    private String staffId;

    public StaffAssignment(String id, String screeningId, String staffId) {
        this.id = id;
        this.screeningId = screeningId;
        this.staffId = staffId;
    }

    public StaffAssignment() {

    }

    public String getId() {
        return id;
    }
    public String getScreeningId() {
        return screeningId;
    }
    public String getStaffId() {
        return staffId;
    }
}
