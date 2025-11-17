package com.example.movie_management_system.service;

import com.example.movie_management_system.model.Customer;
import com.example.movie_management_system.model.Ticket;
import com.example.movie_management_system.repository.CustomerRepositoryInFile;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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

    public void addTicket(String customerId, Ticket ticket) {
//        customerRepository.addTicket(customerId, ticket);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.addTicket(ticket);

            customerRepository.update(customerId, customer);
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
                customerRepository.update(customerId, customer);
        }
        return removedTicket;
    }

    public void updateTicket(String customerId, String ticketId, Ticket ticket) {
//        return customerRepository.updateTicket(customerId, ticketId, ticket);
        Optional<Customer> optionalCustomer = customerRepository.findById(customerId);
        if (optionalCustomer.isPresent()) {
            Customer customer = optionalCustomer.get();
            customer.updateTicket(ticketId, ticket);

            customerRepository.update(customerId, customer);
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