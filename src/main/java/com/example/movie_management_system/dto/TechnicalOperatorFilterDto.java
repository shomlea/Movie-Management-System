package com.example.movie_management_system.dto;

// Assuming your Specialization enum is defined
import com.example.movie_management_system.model.Specialization;

public class TechnicalOperatorFilterDto {

    private String nameQuery;

    private Double minSalary;
    private Double maxSalary;

    private Specialization specialization;

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

    public Specialization getSpecialization() {
        return specialization;
    }

    public void setSpecialization(Specialization specialization) {
        this.specialization = specialization;
    }

    public boolean isEmpty() {
        return (nameQuery == null || nameQuery.trim().isEmpty()) &&
                minSalary == null &&
                maxSalary == null &&
                specialization == null;
    }
}