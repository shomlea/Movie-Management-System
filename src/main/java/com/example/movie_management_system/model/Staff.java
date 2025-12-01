package com.example.movie_management_system.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Staff {
    @Id
    private String id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private double salary; // added attribute

    public Staff(String id, String name, double salary){
        this.id = id;
        this.name = name;
        this.salary = salary;
    }

    public Staff() {

    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public double getSalary() {return salary;}

    public void setId(String id) {
        this.id = id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setSalary(double salary) {this.salary = salary;}
}
