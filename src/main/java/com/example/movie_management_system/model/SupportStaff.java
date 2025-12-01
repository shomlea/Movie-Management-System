package com.example.movie_management_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name = "staffId")
public class SupportStaff extends Staff{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Role is required")
    private Role role;

    public SupportStaff(String name,double salary, Role role) {
        super(name, salary);
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
