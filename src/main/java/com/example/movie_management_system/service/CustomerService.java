package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Customer;
import com.example.movie_management_system.model.Ticket;
import com.example.movie_management_system.repository.CustomerRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }



    public void addTicket(String customerId, Ticket ticket) {
//        customerRepository.addTicket(customerId, ticket);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.addTicket(ticket);

            customerRepository.save(customer);
        }
    }

    public boolean removeTicket(String customerId, String ticketId) {
//        return customerRepository.removeTicket(customerId, ticketId);
        Optional<Customer>  optionalCustomer = customerRepository.findById(customerId);
        boolean removedTicket = false;
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            removedTicket = customer.removeTicket(ticketId);

            if(removedTicket)
                customerRepository.save(customer);
        }
        return removedTicket;
    }

    public void updateTicket(String customerId, String ticketId, Ticket ticket) {
//        return customerRepository.updateTicket(customerId, ticketId, ticket);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.updateTicket(ticketId, ticket);

            customerRepository.save(customer);
        }

    }

    public List<Ticket> getTickets(String customerId) {
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            return customer.getTickets();
        }
        return new ArrayList<>();
    }

    @Transactional
    public void save(String name) {
        String id = UUID.randomUUID().toString();
        Customer customer = new Customer(id, name);
        customerRepository.save(customer);
    }

    @Transactional
    public boolean update(String id, String name) {
//        Customer customer = new Customer(id, name);
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer with ID " + id + " not found."));
        customer.setName(name);
        customerRepository.save(customer);

        return true;
    }
    @Transactional
    public void delete(String id) {
        customerRepository.deleteById(id);
    }

    public Optional<Customer> findById(String id) {
        return customerRepository.findById(id);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }
}