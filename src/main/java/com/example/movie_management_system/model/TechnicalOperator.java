package com.example.movie_management_system.model;

public class TechnicalOperator extends Staff{
    private Specialization specialization;

    public TechnicalOperator(String id, String name, int salary, Specialization specialization) {
        super(id, name, salary);
        this.specialization = specialization;
    }

    public Specialization getSpecialization() {
        return specialization;
    }
    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }
}
