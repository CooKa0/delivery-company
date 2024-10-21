package com.solvd.laba.delivery.services;

import com.solvd.laba.delivery.models.Order;

import java.util.List;

public interface IOrderService {
    void create(Order order);
    Order findById(Long id);
    void update(Order order);
    void delete(Long id);
    List<Order> findAll();
    List<Order> findByCustomerId(Long customerId);
}
