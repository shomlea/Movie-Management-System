package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Customer;
import com.example.movie_management_system.model.Ticket;
import com.example.movie_management_system.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    public List<Ticket> getTickets(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new NoSuchElementException("Customer not found."));
        return customer.getTickets();
    }

    @Transactional
    public Customer save(Customer customer) {
//        Optional<Customer> foundCustomer = customerRepository.findByName(customer.getName());
//        if (foundCustomer.isPresent()) {
//            throw new DataIntegrityViolationException("Customer with name " + customer.getName() + " already exists.");
//        }
        return customerRepository.save(customer);
    }

    @Transactional
    public Customer update(Long id, Customer updatedCustomer) {
        Customer existingCustomer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer with ID " + id + " not found."));

//        Optional<Customer> foundCustomer = customerRepository.findByName(updatedCustomer.getName());
//        if (foundCustomer.isPresent() &&  !foundCustomer.get().getId().equals(existingCustomer.getId())) {
//            throw new DataIntegrityViolationException("Customer with name " + updatedCustomer.getName() + " already exists.");
//        }

        existingCustomer.setName(updatedCustomer.getName());

        return customerRepository.save(existingCustomer);
    }
    @Transactional
    public void delete(Long id) {
        if (!customerRepository.existsById(id)) {
            throw new NoSuchElementException("Customer with ID " + id + " not found.");
        }
        customerRepository.deleteById(id);
    }

    public Optional<Customer> findById(Long id) {
        return customerRepository.findById(id);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}