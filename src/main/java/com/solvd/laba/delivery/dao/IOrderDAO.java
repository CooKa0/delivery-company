package com.solvd.laba.delivery.dao;

import com.solvd.laba.delivery.models.Order;

import java.util.List;

public interface IOrderDAO extends IGenericDAO<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
}
