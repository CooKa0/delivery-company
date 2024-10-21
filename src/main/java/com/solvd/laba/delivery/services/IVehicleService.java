package com.solvd.laba.delivery.services;

import com.solvd.laba.delivery.models.Vehicle;

import java.util.List;

public interface IVehicleService {
    void create(Vehicle vehicle);
    Vehicle findById(Long id);
    void update(Vehicle vehicle);
    void delete(Long id);
    List<Vehicle> findAll();
    List<Vehicle> findByCompanyId(Long companyId);
}
