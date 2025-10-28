package com.example.movie_management_system.service;

import com.example.movie_management_system.repository.Repository;

import java.util.List;

public abstract class Service<T, ID> {
    protected Repository<T, ID> repository;

    public Service(Repository<T, ID> repository) {
        this.repository = repository;
    }

    protected abstract void add(T entity);
    protected abstract void remove(T entity);
    protected abstract void update(T entity);
    protected abstract List<T> getAll();
    protected abstract T findById(ID id);
}
