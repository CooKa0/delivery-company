package com.solvd.laba.delivery.dao.impl;

import com.solvd.laba.delivery.dao.IInvoiceDAO;
import com.solvd.laba.delivery.models.Invoice;
import com.solvd.laba.delivery.connections.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InvoiceDAOImpl implements IInvoiceDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void create(Invoice invoice) {
        if (invoice.getOrderId() == null) {
            throw new IllegalArgumentException("Order ID cannot be null when creating an Invoice");
        }

        String query = "INSERT INTO invoices (order_id, invoice_date, amount) VALUES (?, ?, ?)";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, invoice.getOrderId());
            ps.setTimestamp(2, invoice.getInvoiceDate());
            ps.setDouble(3, invoice.getAmount());
            ps.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Invoice findById(Long id) {
        String query = "SELECT * FROM invoices WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Invoice(rs.getLong("id"), rs.getLong("order_id"),
                        rs.getTimestamp("invoice_date"), rs.getDouble("amount"));
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Invoice invoice) {
        String query = "UPDATE invoices SET order_id = ?, invoice_date = ?, amount = ? WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, invoice.getOrderId());
            ps.setTimestamp(2, invoice.getInvoiceDate());
            ps.setDouble(3, invoice.getAmount());
            ps.setLong(4, invoice.getId());
            ps.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM invoices WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Invoice> findAll() {
        List<Invoice> invoices = new ArrayList<>();
        String query = "SELECT * FROM invoices";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                invoices.add(new Invoice(rs.getLong("id"), rs.getLong("order_id"),
                        rs.getTimestamp("invoice_date"), rs.getDouble("amount")));
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return invoices;
    }
}
