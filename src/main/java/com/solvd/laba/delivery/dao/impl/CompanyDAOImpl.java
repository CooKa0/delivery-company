package com.solvd.laba.delivery.dao.impl;

import com.solvd.laba.delivery.dao.ICompanyDAO;
import com.solvd.laba.delivery.models.*;
import com.solvd.laba.delivery.connections.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CompanyDAOImpl implements ICompanyDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void create(Company company) {
        String query = "INSERT INTO companies (name, location, created_at) VALUES (?, ?, ?)";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, company.getName());
            ps.setString(2, company.getLocation());
            ps.setTimestamp(3, company.getCreatedAt());
            ps.executeUpdate();

            // Get the generated ID and set it back to the company object
            try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    company.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Company findById(Long id) {
        String query = "SELECT * FROM companies WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapCompany(rs);
                }
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Company company) {
        String query = "UPDATE companies SET name = ?, location = ?, created_at = ? WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, company.getName());
            ps.setString(2, company.getLocation());
            ps.setTimestamp(3, company.getCreatedAt());
            ps.setLong(4, company.getId());
            ps.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM companies WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Company> findAll() {
        List<Company> companies = new ArrayList<>();
        String query = "SELECT c.id AS company_id, c.name AS company_name, c.location AS company_location, c.created_at AS company_created_at FROM companies c"; // Updated query with aliases
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                companies.add(mapCompany(rs));
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return companies;
    }

    public Company findCompanyDetails(Long companyId) {
        String query = "SELECT c.id AS company_id, c.name AS company_name, c.location AS company_location, "
                + "c.created_at AS company_created_at, cu.id AS customer_id, cu.first_name AS customer_first_name, "
                + "cu.last_name AS customer_last_name, cu.email AS customer_email, cu.phone_number AS customer_phone, "
                + "cu.created_at AS customer_created_at, "
                + "v.id AS vehicle_id, v.license_plate AS vehicle_license_plate, "
                + "v.vehicle_type AS vehicle_type, v.capacity AS vehicle_capacity, "
                + "o.id AS order_id, o.order_date AS order_date, o.quantity AS quantity, "
                + "o.total_price AS order_total_price, "
                + "i.id AS invoice_id, i.invoice_date AS invoice_date, i.amount AS invoice_amount "
                + "FROM companies c "
                + "LEFT JOIN customers cu ON c.id = cu.company_id "
                + "LEFT JOIN vehicles v ON c.id = v.company_id "
                + "LEFT JOIN orders o ON cu.id = o.customer_id "
                + "LEFT JOIN invoices i ON o.id = i.order_id "
                + "WHERE c.id = ?";

        Company company = null;

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, companyId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (company == null) {
                        company = mapCompany(rs);
                        company.setCustomers(new ArrayList<>());
                        company.setVehicles(new ArrayList<>());
                    }

                    // Map customer details if they exist
                    Long customerId = rs.getLong("customer_id");
                    if (!rs.wasNull()) {
                        Customer customer = Customer.builder()
                                .id(customerId)
                                .firstName(rs.getString("customer_first_name"))
                                .lastName(rs.getString("customer_last_name"))
                                .email(rs.getString("customer_email"))
                                .phoneNumber(rs.getString("customer_phone"))
                                .createdAt(rs.getTimestamp("customer_created_at"))
                                .companyId(companyId)
                                .orders(new ArrayList<>())
                                .build();

                        // Check for duplicates
                        if (company.getCustomers().stream().noneMatch(c -> c.getId().equals(customerId))) {
                            company.getCustomers().add(customer);
                        }

                        // Map order details if they exist
                        Long orderId = rs.getLong("order_id");
                        if (!rs.wasNull()) {
                            Order order = Order.builder()
                                    .id(orderId)
                                    .customerId(customerId)
                                    .companyId(companyId)
                                    .orderDate(rs.getTimestamp("order_date"))
                                    .quantity(rs.getInt("quantity"))
                                    .totalPrice(rs.getDouble("order_total_price"))
                                    .invoice(null)
                                    .build();

                            // Add to customer orders
                            customer.getOrders().add(order);
                        }

                        // Map invoice details if they exist
                        Long invoiceId = rs.getLong("invoice_id");
                        if (!rs.wasNull()) {
                            Invoice invoice = Invoice.builder()
                                    .id(invoiceId)
                                    .orderId(orderId)
                                    .invoiceDate(rs.getTimestamp("invoice_date"))
                                    .amount(rs.getDouble("invoice_amount"))
                                    .build();
                            // Set invoice to the corresponding order
                            if (!customer.getOrders().isEmpty()) {
                                customer.getOrders().get(customer.getOrders().size() - 1).setInvoice(invoice);
                            }
                        }
                    }

                    // Map vehicle details if they exist
                    Long vehicleId = rs.getLong("vehicle_id");
                    if (!rs.wasNull()) {
                        Vehicle vehicle = Vehicle.builder()
                                .id(vehicleId)
                                .licensePlate(rs.getString("vehicle_license_plate"))
                                .vehicleType(rs.getString("vehicle_type"))
                                .capacity(rs.getDouble("vehicle_capacity"))
                                .companyId(companyId)
                                .build();

                        // Prevent duplicates
                        if (company.getVehicles().stream().noneMatch(v -> v.getId().equals(vehicleId))) {
                            company.getVehicles().add(vehicle);
                        }
                    }
                }
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return company;
    }

    // mapping method for ResultSet to Company object
    private static Company mapCompany(ResultSet rs) throws SQLException {
        return Company.builder()
                .id(rs.getLong("company_id"))
                .name(rs.getString("company_name"))
                .location(rs.getString("company_location"))
                .createdAt(rs.getTimestamp("company_created_at"))
                .build();
    }

}
