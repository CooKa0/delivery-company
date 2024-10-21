package com.solvd.laba.delivery.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MaintenanceRecord {
    private Long id;
    private Long vehicleId;
    private String description;
    private Timestamp maintenanceDate;
    private double cost;
}
