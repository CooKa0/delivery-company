package com.solvd.laba.delivery.dao.impl;

import com.solvd.laba.delivery.dao.ICustomerDAO;
import com.solvd.laba.delivery.models.Customer;
import com.solvd.laba.delivery.connections.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAOImpl implements ICustomerDAO {
    private ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void create(Customer customer) {
        String query = "INSERT INTO customers (company_id, first_name, last_name, email, phone_number, created_at) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setLong(1, customer.getCompanyId());
            ps.setString(2, customer.getFirstName());
            ps.setString(3, customer.getLastName());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getPhoneNumber());
            ps.setTimestamp(6, customer.getCreatedAt());
            ps.executeUpdate();

            // Retrieve the generated ID
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    customer.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Customer findById(Long id) {
        String query = "SELECT * FROM customers WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setCompanyId(rs.getLong("company_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setCreatedAt(rs.getTimestamp("created_at"));
                return customer;
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Customer findByEmail(String email) {
        String query = "SELECT * FROM customers WHERE email = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setCompanyId(rs.getLong("company_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setCreatedAt(rs.getTimestamp("created_at"));
                return customer;
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Customer customer) {
        String query = "UPDATE customers SET company_id = ?, first_name = ?, last_name = ?, email = ?, phone_number = ?, created_at = ? WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, customer.getCompanyId());
            ps.setString(2, customer.getFirstName());
            ps.setString(3, customer.getLastName());
            ps.setString(4, customer.getEmail());
            ps.setString(5, customer.getPhoneNumber());
            ps.setTimestamp(6, customer.getCreatedAt());
            ps.setLong(7, customer.getId());
            ps.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM customers WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Customer> findAll() {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Customer customer = new Customer();
                customer.setId(rs.getLong("id"));
                customer.setCompanyId(rs.getLong("company_id"));
                customer.setFirstName(rs.getString("first_name"));
                customer.setLastName(rs.getString("last_name"));
                customer.setEmail(rs.getString("email"));
                customer.setPhoneNumber(rs.getString("phone_number"));
                customer.setCreatedAt(rs.getTimestamp("created_at"));
                customers.add(customer);

            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public List<Customer> findByCompanyId(Long companyId) {
        List<Customer> customers = new ArrayList<>();
        String query = "SELECT * FROM customers WHERE company_id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, companyId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Customer customer = new Customer();
                    customer.setId(rs.getLong("id"));
                    customer.setCompanyId(rs.getLong("company_id"));
                    customer.setFirstName(rs.getString("first_name"));
                    customer.setLastName(rs.getString("last_name"));
                    customer.setEmail(rs.getString("email"));
                    customer.setPhoneNumber(rs.getString("phone_number"));
                    customer.setCreatedAt(rs.getTimestamp("created_at"));
                    customers.add(customer);
                }
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return customers;
    }
}
