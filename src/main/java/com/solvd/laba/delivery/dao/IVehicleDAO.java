package com.solvd.laba.delivery.dao;

import com.solvd.laba.delivery.models.Vehicle;

import java.util.List;

public interface IVehicleDAO extends IGenericDAO<Vehicle, Long> {
    List<Vehicle> findByCompanyId(Long companyId);
}
