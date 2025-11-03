package com.example.movie_management_system.model;

import jakarta.persistence.Entity;

public class SupportStaff extends Staff{
    private Role role;

    public SupportStaff(String id, String name,int salary, Role role) {
        super(id, name, salary);
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
