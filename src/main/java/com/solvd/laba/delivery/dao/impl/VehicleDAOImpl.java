package com.solvd.laba.delivery.dao.impl;

import com.solvd.laba.delivery.dao.IVehicleDAO;
import com.solvd.laba.delivery.models.Vehicle;
import com.solvd.laba.delivery.connections.ConnectionPool;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDAOImpl implements IVehicleDAO {
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public void create(Vehicle vehicle) {
        System.out.println("Creating vehicle: " + vehicle.getLicensePlate());

        // Check if a vehicle with the same license plate exists
        if (licensePlateExists(vehicle.getLicensePlate())) {
            throw new IllegalArgumentException("A vehicle with this license plate already exists: " + vehicle.getLicensePlate());
        }

        String query = "INSERT INTO vehicles (company_id, license_plate, vehicle_type, capacity) VALUES (?, ?, ?, ?)";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, vehicle.getCompanyId());
            ps.setString(2, vehicle.getLicensePlate());
            ps.setString(3, vehicle.getVehicleType());
            ps.setDouble(4, vehicle.getCapacity());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Vehicle created successfully: " + vehicle);
            } else {
                System.err.println("No vehicle was created.");
            }
        } catch (SQLException | InterruptedException e) {
            System.err.println("Failed to create vehicle: " + vehicle.getLicensePlate() + " - " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public Vehicle findById(Long id) {
        String query = "SELECT * FROM vehicles WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return Vehicle.builder()
                        .id(rs.getLong("id"))
                        .companyId(rs.getLong("company_id"))
                        .licensePlate(rs.getString("license_plate"))
                        .vehicleType(rs.getString("vehicle_type"))
                        .capacity(rs.getDouble("capacity"))
                        .build();
            }
        } catch (SQLException | InterruptedException e) {
            System.err.println("Failed to find vehicle by ID: " + id + " - " + e.getMessage());
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void update(Vehicle vehicle) {
        String query = "UPDATE vehicles SET company_id = ?, license_plate = ?, vehicle_type = ?, capacity = ? WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, vehicle.getCompanyId());
            ps.setString(2, vehicle.getLicensePlate());
            ps.setString(3, vehicle.getVehicleType());
            ps.setDouble(4, vehicle.getCapacity());
            ps.setLong(5, vehicle.getId());

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Vehicle updated successfully: " + vehicle);
            } else {
                System.err.println("No vehicle was updated. ID may not exist: " + vehicle.getId());
            }
        } catch (SQLException | InterruptedException e) {
            System.err.println("Failed to update vehicle: " + vehicle.getId() + " - " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void delete(Long id) {
        String query = "DELETE FROM vehicles WHERE id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, id);

            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Vehicle deleted successfully with ID: " + id);
            } else {
                System.err.println("No vehicle was deleted. ID may not exist: " + id);
            }
        } catch (SQLException | InterruptedException e) {
            System.err.println("Failed to delete vehicle: " + id + " - " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Vehicle> findAll() {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                vehicles.add(Vehicle.builder()
                        .id(rs.getLong("id"))
                        .companyId(rs.getLong("company_id"))
                        .licensePlate(rs.getString("license_plate"))
                        .vehicleType(rs.getString("vehicle_type"))
                        .capacity(rs.getDouble("capacity"))
                        .build());
            }
        } catch (SQLException | InterruptedException e) {
            System.err.println("Failed to retrieve vehicles - " + e.getMessage());
            e.printStackTrace();
        }
        return vehicles;
    }

    private boolean licensePlateExists(String licensePlate) {
        String query = "SELECT id FROM vehicles WHERE license_plate = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, licensePlate);
            try (ResultSet rs = ps.executeQuery()) {
                boolean exists = rs.next();
                System.out.println("Checking if license plate exists (" + licensePlate + "): " + exists);
                return exists;
            }
        } catch (SQLException | InterruptedException e) {
            System.err.println("Failed to check if license plate exists: " + licensePlate + " - " + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public List<Vehicle> findByCompanyId(Long companyId) {
        List<Vehicle> vehicles = new ArrayList<>();
        String query = "SELECT * FROM vehicles WHERE company_id = ?";
        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setLong(1, companyId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    vehicles.add(Vehicle.builder()
                            .id(rs.getLong("id"))
                            .companyId(rs.getLong("company_id"))
                            .licensePlate(rs.getString("license_plate"))
                            .vehicleType(rs.getString("vehicle_type"))
                            .capacity(rs.getDouble("capacity"))
                            .build());
                }
            }
        } catch (SQLException | InterruptedException e) {
            System.err.println("Failed to retrieve vehicles for company ID: " + companyId + " - " + e.getMessage());
            e.printStackTrace();
        }
        return vehicles;
    }
}
