package com.example.movie_management_system.repository;

import com.example.movie_management_system.model.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class CustomerRepositoryInMemory extends BaseRepositoryInMemory<Customer, String> {
    public CustomerRepositoryInMemory() {
        super(Customer::getId);
    }
}
