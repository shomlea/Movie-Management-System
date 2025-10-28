package com.example.movie_management_system.service;

import com.example.movie_management_system.repository.Repository;

import java.util.List;

public abstract class Service<T, ID> {

    protected Repository<T, ID> repository;

    protected Service(Repository<T, ID> repository) {
        this.repository = repository;
    }


    public abstract List<T> getAll();

    public abstract T findById(ID id);

    public abstract T add(T t);

    public abstract T remove(T t);

}
