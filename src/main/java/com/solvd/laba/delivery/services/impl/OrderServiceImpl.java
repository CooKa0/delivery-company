package com.solvd.laba.delivery.services.impl;

import com.solvd.laba.delivery.dao.IOrderDAO;
import com.solvd.laba.delivery.models.Order;
import com.solvd.laba.delivery.services.IOrderService;

import java.util.List;

public class OrderServiceImpl implements IOrderService {
    private final IOrderDAO orderDAO;

    public OrderServiceImpl(IOrderDAO orderDAO) {
        this.orderDAO = orderDAO;
    }

    @Override
    public void create(Order order) {
        orderDAO.create(order);
    }

    @Override
    public Order findById(Long id) {
        return orderDAO.findById(id);
    }

    @Override
    public void update(Order order) {
        orderDAO.update(order);
    }

    @Override
    public void delete(Long id) {
        orderDAO.delete(id);
    }

    @Override
    public List<Order> findAll() {
        return orderDAO.findAll();
    }
}
