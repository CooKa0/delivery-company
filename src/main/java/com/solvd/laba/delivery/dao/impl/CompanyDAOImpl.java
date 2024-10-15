package com.solvd.laba.delivery.dao.impl;

import com.solvd.laba.delivery.dao.ICompanyDAO;
import com.solvd.laba.delivery.models.Company;
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
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return new Company(rs.getLong("id"), rs.getString("name"),
                        rs.getString("location"), rs.getTimestamp("created_at"));
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
        String query = "SELECT * FROM companies";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                companies.add(new Company(rs.getLong("id"), rs.getString("name"),
                        rs.getString("location"), rs.getTimestamp("created_at")));
            }
        } catch (SQLException | InterruptedException e) {
            e.printStackTrace();
        }
        return companies;
    }
}
