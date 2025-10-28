package com.example.movie_management_system.repository;

import java.util.ArrayList;
import java.util.List;

public abstract class Repository<T, ID> {
    private List<T> all;

    public Repository() {
        all = new ArrayList<>();
    }

    public List<T> getAll() {
        return all;
    }
    public void add(T t) {
        all.add(t);
    }
    public void remove(T t) {
        all.remove(t);
    }

    public abstract T findById(String id);

}
