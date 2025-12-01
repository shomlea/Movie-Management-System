package com.example.movie_management_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

@Entity
@PrimaryKeyJoinColumn(name = "staffId")
public class TechnicalOperator extends Staff{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @NotNull(message = "Specialization is required")
    private Specialization specialization;

    public TechnicalOperator(String name, double salary, Specialization specialization) {
        super(name, salary);
        this.specialization = specialization;
    }

    public TechnicalOperator() {}

    public Specialization getSpecialization() {
        return specialization;
    }
    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
