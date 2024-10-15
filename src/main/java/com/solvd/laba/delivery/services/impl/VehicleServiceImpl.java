package com.solvd.laba.delivery.services.impl;

import com.solvd.laba.delivery.dao.IVehicleDAO;
import com.solvd.laba.delivery.models.Vehicle;
import com.solvd.laba.delivery.services.IVehicleService;

import java.util.List;

public class VehicleServiceImpl implements IVehicleService {
    private final IVehicleDAO vehicleDAO;

    public VehicleServiceImpl(IVehicleDAO vehicleDAO) {
        this.vehicleDAO = vehicleDAO;
    }

    @Override
    public void create(Vehicle vehicle) {
        vehicleDAO.create(vehicle);
    }

    @Override
    public Vehicle findById(Long id) {
        return vehicleDAO.findById(id);
    }

    @Override
    public void update(Vehicle vehicle) {
        vehicleDAO.update(vehicle);
    }

    @Override
    public void delete(Long id) {
        vehicleDAO.delete(id);
    }

    @Override
    public List<Vehicle> findAll() {
        return vehicleDAO.findAll();
    }
}
