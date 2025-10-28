package com.example.movie_management_system.model;

public class StaffAssignment {
    private String id;
    private String screeningId;
    private String staffId;

    public StaffAssignment(String id, String screeningId, String staffId) {
        this.id = id;
        this.screeningId = screeningId;
        this.staffId = staffId;
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
