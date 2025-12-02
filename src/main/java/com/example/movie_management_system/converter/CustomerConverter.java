package com.example.movie_management_system.converter;

import com.example.movie_management_system.model.Customer;
import com.example.movie_management_system.service.CustomerService;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import java.util.NoSuchElementException;

@Component
public class CustomerConverter implements Converter<Long, Customer> {

    private final CustomerService customerService;

    public CustomerConverter(CustomerService customerService) {
        this.customerService = customerService;
    }

    @Override
    public Customer convert(Long id) {
        if (id == null) return null;

        return customerService.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Customer with ID " + id + " not found."));
    }
}