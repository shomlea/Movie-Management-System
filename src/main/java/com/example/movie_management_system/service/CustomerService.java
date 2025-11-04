package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Customer;
import com.example.movie_management_system.repository.CustomerRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public Customer save(String name) {
        String id = UUID.randomUUID().toString();
        Customer customer = new Customer(id, name);
        return customerRepository.save(customer);
    }

    public void remove(String id) {
        customerRepository.remove(id);
    }

    public Optional<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    public List<Customer> getAll() {
        return customerRepository.getall();
    }
}