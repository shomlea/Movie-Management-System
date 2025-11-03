package com.example.movie_management_system.model;

import jakarta.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Staff {
    @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;
    private int salary; // added attribute

    public Staff(String id, String name, int salary){
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
}
