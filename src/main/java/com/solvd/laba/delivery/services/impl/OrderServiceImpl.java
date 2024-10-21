package com.solvd.laba.delivery.services.impl;

import com.solvd.laba.delivery.dao.IOrderDAO;
import com.solvd.laba.delivery.models.Invoice;
import com.solvd.laba.delivery.models.Order;
import com.solvd.laba.delivery.services.IOrderService;
import com.solvd.laba.delivery.services.IInvoiceService;

import java.util.List;

public class OrderServiceImpl implements IOrderService {
    private final IOrderDAO orderDAO;
    private final IInvoiceService invoiceService;

    public OrderServiceImpl(IOrderDAO orderDAO, IInvoiceService invoiceService) {
        this.orderDAO = orderDAO;
        this.invoiceService = invoiceService;
    }

    @Override
    public void create(Order order) {
        orderDAO.create(order);
    }

    @Override
    public Order findById(Long id) {
        Order order = orderDAO.findById(id);
        if (order != null) {
            Invoice invoice = invoiceService.findByOrderId(id);
            order.setInvoice(invoice);
        }
        return order;
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
        List<Order> orders = orderDAO.findAll();
        for (Order order : orders) {
            Invoice invoice = invoiceService.findByOrderId(order.getId());
            order.setInvoice(invoice);
        }
        return orders;
    }
    public List<Order> findByCustomerId(Long customerId) {
        return orderDAO.findByCustomerId(customerId);
    }
}
