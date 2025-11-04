package com.example.movie_management_system.repository;

import java.util.List;
import java.util.Optional;

public interface AbstractRepository<T, ID> {

    void add(T entity);

    List<T> getAll();

    Optional<T> findById(ID id);

    boolean remove(ID id);

    boolean update(ID id, T updated);

    boolean existsById(ID id);

    long count();

    void clear();


    default List<T> getAll(int offset, int limit) {
        List<T> all = getAll();
        int from = Math.max(0, Math.min(offset, all.size()));
        int to = Math.max(from, Math.min(from + limit, all.size()));
        return all.subList(from, to);
    }
}
