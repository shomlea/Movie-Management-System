package com.example.movie_management_system.repository.deprecated;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

import java.util.function.Function;

public class BaseRepositoryInFile<T, ID> implements AbstractRepository<T, ID> {
    private Map<ID, T> entities = new HashMap<>();

    private final Function<T, ID> idExtractor;

    private final String filePath;

    private final Class<T> entityType;

    private final ObjectMapper objectMapper;

    public BaseRepositoryInFile(Function<T, ID> idExtractor, String filePath, Class<T> entityType) {
        if (idExtractor == null) {
            throw new IllegalArgumentException("idExtractor cannot be null");
        }
        this.idExtractor = idExtractor;
        this.filePath = filePath; //TODO check file in load and save
        this.entityType = entityType;

        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule());
        this.objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        load();
    }

    public void load() {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs(); // Create necessary directories
        }

        if (!(file.exists())) {

            System.out.println("File " + filePath + " does not exist or is empty. Creating new data file.");
            try {
                if (file.createNewFile()) {
                    System.out.println("Created new data file: " + file.getAbsolutePath());
                }
            } catch (IOException e) {
                throw new RuntimeException("Could not ensure data file exists: " + filePath, e);
            }
            return;
        }
        if(!(file.length() > 0)) {
            System.out.println("No data to read from " + filePath);
            return;
        }

        try (Reader reader = Files.newBufferedReader(file.toPath())) {
//            TypeReference<Map<ID, T>> typeRef = new TypeReference<>() {};
//
//            entities = objectMapper.readValue(reader, typeRef);

            com.fasterxml.jackson.databind.JavaType keyType = objectMapper.getTypeFactory().constructType(String.class);

            com.fasterxml.jackson.databind.JavaType valueType = objectMapper.getTypeFactory().constructType(entityType);

            com.fasterxml.jackson.databind.JavaType mapType = objectMapper.getTypeFactory()
                    .constructMapType(HashMap.class, keyType, valueType);

//            @SuppressWarnings("unchecked")

            entities = objectMapper.readValue(reader, mapType);

            System.out.println("Data loaded successfully from " + file);
        } catch (FileNotFoundException e) {

            System.err.println("File " + filePath + " not found: " + e.getMessage());
            entities = new HashMap<>();
        } catch (IOException e) {
            System.err.println("Error loading data from file " + filePath + ": " + e.getMessage());
            entities = new HashMap<>();
        }

    }

    public void save() {
        File file = new File(filePath);
        File parentDir = file.getParentFile();
        if (parentDir != null && !parentDir.exists()) {
            parentDir.mkdirs();
        }


        try (Writer writer = Files.newBufferedWriter(file.toPath())) {
            objectMapper.writeValue(writer, entities);
            System.out.println("Data saved successfully to " + file);
        } catch (IOException e) {
            System.err.println("Error saving data to file: " + e.getMessage());
            throw new RuntimeException("Could not save data to file", e);
        }
    }

    @Override
    public void add(T entity) {
        if(entity == null)
            throw new IllegalArgumentException("entity is null");
        ID id = idExtractor.apply(entity);
        if (id == null)
            throw new IllegalArgumentException("entity ID cannot be null");
        entities.putIfAbsent(id, entity);
        save();
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
        boolean removed = entities.remove(id) != null;
        if(removed) {
            save();
        }
        return removed;
    }

    @Override
    public boolean update(ID id, T updated) {
//        if(updated == null)
//            throw new IllegalArgumentException("updated entity is null");
//        ID updatedId = idExtractor.apply(updated);
//        if(updatedId == null)
//            throw new IllegalArgumentException("updated entity ID cannot be null");
//        if(!updatedId.equals(id))
//            throw new IllegalArgumentException("updated entity id must match path id");
//        save();
//        return entities.replace(id, updated) != null;

        if(updated == null)
            throw new IllegalArgumentException("updated entity is null");

        ID updatedId = idExtractor.apply(updated);
        if(updatedId == null)
            throw new IllegalArgumentException("updated entity ID cannot be null");
        if(!updatedId.equals(id))
            throw new IllegalArgumentException("updated entity id must match path id");

        boolean replaced = entities.replace(id, updated) != null;


        if (replaced) {
            save();
        }

        return replaced;
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
