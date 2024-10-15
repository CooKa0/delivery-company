package com.solvd.laba.delivery.services.impl;

import com.solvd.laba.delivery.dao.ICustomerDAO;
import com.solvd.laba.delivery.models.Customer;
import com.solvd.laba.delivery.services.ICustomerService;

import java.util.List;

public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerDAO customerDAO;

    public CustomerServiceImpl(ICustomerDAO customerDAO) {
        this.customerDAO = customerDAO;
    }

    @Override
    public void create(Customer customer) {
        customerDAO.create(customer);
    }

    @Override
    public Customer findById(Long id) {
        return customerDAO.findById(id);
    }

    @Override
    public void update(Customer customer) {
        customerDAO.update(customer);
    }

    @Override
    public void delete(Long id) {
        customerDAO.delete(id);
    }

    @Override
    public List<Customer> findAll() {
        return customerDAO.findAll();
    }

    @Override
    public Customer findByEmail(String email) {
        return customerDAO.findByEmail(email);
    }
}
