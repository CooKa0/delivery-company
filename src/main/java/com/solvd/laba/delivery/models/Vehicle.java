package com.solvd.laba.delivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Vehicle {
    private Long id;
    private Long companyId;
    private String licensePlate;
    private String vehicleType;
    private Double capacity;
}
