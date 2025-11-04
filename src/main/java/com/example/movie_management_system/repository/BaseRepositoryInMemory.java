package com.example.movie_management_system.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

public class BaseRepositoryInMemory<T, ID> implements AbstractRepository<T, ID> {

    private final Map<ID, T> entities = new ConcurrentHashMap<>();

    private final Function<T, ID> idExtractor;

    public BaseRepositoryInMemory(Function<T, ID> idExtractor) {
        if (idExtractor == null) {
            throw new IllegalArgumentException("idExtractor cannot be null");
        }
        this.idExtractor = idExtractor;
    }



    @Override
    public void add(T entity) {
        if(entity == null)
            throw new IllegalArgumentException("entity is null");
        ID id = idExtractor.apply(entity);
        if (id == null)
            throw new IllegalArgumentException("entity ID cannot be null");
        entities.putIfAbsent(id, entity);
    }

    @Override
    public List<T> getAll() {
        return new ArrayList<>(entities.values());
    }

    @Override
    public Optional<T> findById(ID id) {
        return Optional.ofNullable(entities.get(id));
    }

    @Override
    public boolean remove(ID id) {
        return entities.remove(id) != null;
    }

    @Override
    public boolean update(ID id, T updated) {
        if(updated == null)
            throw new IllegalArgumentException("updated entity is null");
        ID updatedId = idExtractor.apply(updated);
        if(updatedId == null)
            throw new IllegalArgumentException("updated entity ID cannot be null");
        if(!updatedId.equals(id))
            throw new IllegalArgumentException("updated entity id must match path id");
        return entities.replace(id, updated) != null;
    }

    @Override
    public boolean existsById(ID id) {
        return entities.containsKey(id);
    }

    @Override
    public long count() {
        return entities.size();
    }

    @Override
    public void clear() {
        entities.clear();
    }
}
