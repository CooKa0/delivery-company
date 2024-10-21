package com.solvd.laba.delivery.staxParser;


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
public class Company {
    private Long id;
    private String name;
    private String location;
    private Timestamp createdAt;
    private List<Customer> customers;
    private List<Vehicle> vehicles;
}
