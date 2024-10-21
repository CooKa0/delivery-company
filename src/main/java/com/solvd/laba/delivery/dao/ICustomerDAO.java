package com.solvd.laba.delivery.dao;

import com.solvd.laba.delivery.models.Customer;

import java.util.List;

public interface ICustomerDAO extends IGenericDAO<Customer, Long> {
    Customer findByEmail(String email);
    List<Customer> findByCompanyId(Long companyId);
}
