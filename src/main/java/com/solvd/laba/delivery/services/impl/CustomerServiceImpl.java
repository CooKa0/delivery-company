package com.solvd.laba.delivery.services.impl;

import com.solvd.laba.delivery.dao.ICustomerDAO;
import com.solvd.laba.delivery.models.Customer;
import com.solvd.laba.delivery.models.Order;
import com.solvd.laba.delivery.services.ICustomerService;
import com.solvd.laba.delivery.services.IOrderService;

import java.util.List;

public class CustomerServiceImpl implements ICustomerService {
    private final ICustomerDAO customerDAO;
    private final IOrderService orderService;

    public CustomerServiceImpl(ICustomerDAO customerDAO, IOrderService orderService) {
        this.customerDAO = customerDAO;
        this.orderService = orderService;
    }

    @Override
    public void create(Customer customer) {
        customerDAO.create(customer);
    }

    @Override
    public Customer findById(Long id) {
        Customer customer = customerDAO.findById(id);
        if (customer != null) {
            List<Order> orders = orderService.findByCustomerId(id);
            customer.setOrders(orders);
        }
        return customer;
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
        List<Customer> customers = customerDAO.findAll();
        for (Customer customer : customers) {
            List<Order> orders = orderService.findByCustomerId(customer.getId());
            customer.setOrders(orders);
        }
        return customers;
    }

    @Override
    public Customer findByEmail(String email) {
        return customerDAO.findByEmail(email);
    }

    @Override
    public List<Customer> findByCompanyId(Long companyId) {
        return customerDAO.findByCompanyId(companyId);
    }
}
