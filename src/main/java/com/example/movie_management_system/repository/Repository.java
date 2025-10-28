package com.example.movie_management_system.repository;



import java.util.List;

public abstract class Repository<T, ID> {
    public abstract T findById(ID id);
    public abstract void add(T entity);
    public abstract void remove(T entity);
    public abstract List<T> getAll();
}
