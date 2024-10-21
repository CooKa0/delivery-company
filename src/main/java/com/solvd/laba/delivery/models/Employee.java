package com.solvd.laba.delivery.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {
    private Long id;
    private Long companyId;
    private String firstName;
    private String lastName;
    private String role;
    private Timestamp hireDate;
    private List<Delivery> deliveries;
}
