package com.solvd.laba.delivery.dao.impl;

import com.solvd.laba.delivery.dao.IOrderDAO;
import com.solvd.laba.delivery.models.Order;
import com.solvd.laba.delivery.connections.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrderDAOImpl implements IOrderDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void create(Order order) {
        String query = "INSERT INTO orders (customer_id, company_id, order_date, quantity, total_price) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, order.getCustomerId());
            ps.setLong(2, order.getCompanyId());
            ps.setTimestamp(3, order.getOrderDate());
            ps.setInt(4, order.getQuantity());
            ps.setDouble(5, order.getTotalPrice());
            ps.executeUpdate();

            // Retrieve the generated Order ID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    order.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Order findById(Long id) {
        String query = "SELECT * FROM orders WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Order.builder()
                        .id(rs.getLong("id"))
                        .customerId(rs.getLong("customer_id"))
                        .orderDate(rs.getTimestamp("order_date"))
                        .totalPrice(rs.getDouble("total_price"))
                        .build();
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Order order) {
        String query = "UPDATE orders SET customer_id = ?, order_date = ?, total_price = ? WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, order.getCustomerId());
            ps.setTimestamp(2, order.getOrderDate());
            ps.setDouble(3, order.getTotalPrice());
            ps.setLong(4, order.getId());
            ps.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM orders WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Order> findAll() {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                orders.add(Order.builder()
                        .id(rs.getLong("id"))
                        .customerId(rs.getLong("customer_id"))
                        .orderDate(rs.getTimestamp("order_date"))
                        .totalPrice(rs.getDouble("total_price"))
                        .build());
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return orders;
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        List<Order> orders = new ArrayList<>();
        String query = "SELECT * FROM orders WHERE customer_id = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, customerId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                orders.add(Order.builder()
                        .id(rs.getLong("id"))
                        .customerId(rs.getLong("customer_id"))
                        .companyId(rs.getLong("company_id"))
                        .orderDate(rs.getTimestamp("order_date"))
                        .quantity(rs.getInt("quantity"))
                        .totalPrice(rs.getDouble("total_price"))
                        .build());
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return orders;
    }
}
