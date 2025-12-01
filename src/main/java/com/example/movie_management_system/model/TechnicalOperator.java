package com.example.movie_management_system.model;

import jakarta.persistence.*;

@Entity
@PrimaryKeyJoinColumn(name = "staffId")
public class TechnicalOperator extends Staff{
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Specialization specialization;

    public TechnicalOperator(String id, String name, double salary, Specialization specialization) {
        super(id, name, salary);
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
