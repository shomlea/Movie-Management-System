package com.example.movie_management_system.model;

public abstract class Staff {
    private String id;
    private String name;

    public Staff(String id, String name){
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
