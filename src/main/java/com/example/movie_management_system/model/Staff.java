package com.example.movie_management_system.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Staff {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @NotBlank(message = "Staff name is required.")
    private String name;

    @Column(nullable = false)
    @Positive(message = "Salary should be a positive number")
    private double salary; // added attribute

    public Staff(String name, double salary){
        this.name = name;
        this.salary = salary;
    }

    public Staff() {

    }

    public Long getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getSalary() {return salary;}

    public void setId(Long id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSalary(double salary) {this.salary = salary;}
}
