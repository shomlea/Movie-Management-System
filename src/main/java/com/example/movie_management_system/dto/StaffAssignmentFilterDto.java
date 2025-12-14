package com.example.movie_management_system.dto;

public class StaffAssignmentFilterDto {

    private String staffNameQuery;

    public String getStaffNameQuery() {
        return staffNameQuery;
    }

    public void setStaffNameQuery(String staffNameQuery) {
        this.staffNameQuery = staffNameQuery;
    }

    public boolean isEmpty() {
        return (staffNameQuery == null || staffNameQuery.trim().isEmpty());
    }
}