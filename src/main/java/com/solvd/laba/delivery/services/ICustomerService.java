package com.solvd.laba.delivery.services;

import com.solvd.laba.delivery.models.Customer;

import java.util.List;

public interface ICustomerService {
    void create(Customer customer);
    Customer findById(Long id);
    Customer findByEmail(String email);
    void update(Customer customer);
    void delete(Long id);
    List<Customer> findAll();
}
