package com.solvd.laba.delivery.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Vehicle {
    private Long id;
    private Long companyId;
    private String licensePlate;
    private String vehicleType;
    private double capacity;
    private List<MaintenanceRecord> maintenanceRecords;
}
