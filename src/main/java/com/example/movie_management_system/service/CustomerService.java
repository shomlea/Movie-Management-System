package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Customer;
import com.example.movie_management_system.repository.CustomerRepositoryInFile;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepositoryInFile customerRepository;

    public CustomerService(CustomerRepositoryInFile customerRepository) {
        this.customerRepository = customerRepository;
    }


    public void add(String name) {
        String id = UUID.randomUUID().toString();
        Customer customer = new Customer(id, name);
        customerRepository.add(customer);
    }

    public boolean update(String id, String name) {
        Customer customer = new Customer(id, name);
        return customerRepository.update(id, customer);
    }

    public void remove(String id) {
        customerRepository.remove(id);
    }

    public Optional<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAll() {
        return customerRepository.getAll();
    }
}