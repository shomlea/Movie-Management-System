package com.example.movie_management_system.model;

import jakarta.persistence.*;

@Entity
public class StaffAssignment {
    @Id
    private String id;

    @Column(nullable = false)
    private String screeningId;

    @Column(nullable = false)
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

    public void setId(String id) {
        this.id = id;
    }
    public void setScreeningId(String screeningId) {
        this.screeningId = screeningId;
    }
    public void setStaffId(String staffId) {
        this.staffId = staffId;
    }
}
