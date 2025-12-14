package com.example.movie_management_system.dto;

import com.example.movie_management_system.model.Role;

public class SupportStaffFilterDto {

    private String nameQuery;

    private Double minSalary;
    private Double maxSalary;

    private Role role; // The enum type for filtering

    public String getNameQuery() {
        return nameQuery;
    }

    public void setNameQuery(String nameQuery) {
        this.nameQuery = nameQuery;
    }

    public Double getMinSalary() {
        return minSalary;
    }

    public void setMinSalary(Double minSalary) {
        this.minSalary = minSalary;
    }

    public Double getMaxSalary() {
        return maxSalary;
    }

    public void setMaxSalary(Double maxSalary) {
        this.maxSalary = maxSalary;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public boolean isEmpty() {
        return (nameQuery == null || nameQuery.trim().isEmpty()) &&
                minSalary == null &&
                maxSalary == null &&
                role == null;
    }
}