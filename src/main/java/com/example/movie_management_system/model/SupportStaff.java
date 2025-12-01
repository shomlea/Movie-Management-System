package com.example.movie_management_system.model;

import jakarta.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "staffId")
public class SupportStaff extends Staff{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role role;

    public SupportStaff(String id, String name,double salary, Role role) {
        super(id, name, salary);
        this.role = role;
    }

    public SupportStaff() {}

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
