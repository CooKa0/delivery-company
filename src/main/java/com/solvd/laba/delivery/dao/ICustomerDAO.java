package com.solvd.laba.delivery.dao;

import com.solvd.laba.delivery.models.Customer;

public interface ICustomerDAO extends IGenericDAO<Customer, Long> {
    Customer findByEmail(String email);
}
