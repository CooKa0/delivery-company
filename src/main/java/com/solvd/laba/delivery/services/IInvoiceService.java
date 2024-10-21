package com.solvd.laba.delivery.services;

import com.solvd.laba.delivery.models.Invoice;

import java.util.List;

public interface IInvoiceService {
    void create(Invoice invoice);
    Invoice findById(Long id);
    void update(Invoice invoice);
    void delete(Long id);
    List<Invoice> findAll();
    Invoice findByOrderId(Long orderId);
}
