package com.example.movie_management_system.repository.deprecated;

import com.example.movie_management_system.model.Customer;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

@Repository
@Primary
public class CustomerRepositoryInFile extends BaseRepositoryInFile<Customer, String>{

    public CustomerRepositoryInFile() {
        super(Customer::getId, "data/customers.json", Customer.class);
    }

}
