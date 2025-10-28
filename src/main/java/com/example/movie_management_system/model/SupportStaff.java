package com.example.movie_management_system.model;

public class SupportStaff extends Staff{
    private Role role;

    public SupportStaff(String id, String name, Role role) {
        super(id, name);
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
