package com.solvd.laba.delivery.services.impl;

import com.solvd.laba.delivery.dao.IInvoiceDAO;
import com.solvd.laba.delivery.models.Invoice;
import com.solvd.laba.delivery.services.IInvoiceService;

import java.util.List;

public class InvoiceServiceImpl implements IInvoiceService {
    private final IInvoiceDAO invoiceDAO;

    public InvoiceServiceImpl(IInvoiceDAO invoiceDAO) {
        this.invoiceDAO = invoiceDAO;
    }

    @Override
    public void create(Invoice invoice) {
        invoiceDAO.create(invoice);
    }

    @Override
    public Invoice findById(Long id) {
        return invoiceDAO.findById(id);
    }

    @Override
    public void update(Invoice invoice) {
        invoiceDAO.update(invoice);
    }

    @Override
    public void delete(Long id) {
        invoiceDAO.delete(id);
    }

    @Override
    public List<Invoice> findAll() {
        return invoiceDAO.findAll();
    }

    @Override
    public Invoice findByOrderId(Long orderId) {
        return invoiceDAO.findByOrderId(orderId);
    }
}
