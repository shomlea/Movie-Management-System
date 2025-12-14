package com.example.movie_management_system.dto;

import java.util.Objects;

public class TicketFilterDto {

    private String customerNameQuery;

    private Double minPrice;

    private Double maxPrice;


    public String getCustomerNameQuery() {
        return customerNameQuery;
    }

    public void setCustomerNameQuery(String customerNameQuery) {
        this.customerNameQuery = customerNameQuery;
    }

    public Double getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(Double minPrice) {
        this.minPrice = minPrice;
    }

    public Double getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(Double maxPrice) {
        this.maxPrice = maxPrice;
    }


    public boolean isEmpty() {
        return (customerNameQuery == null || customerNameQuery.trim().isEmpty()) &&
                minPrice == null &&
                maxPrice == null;
    }
}