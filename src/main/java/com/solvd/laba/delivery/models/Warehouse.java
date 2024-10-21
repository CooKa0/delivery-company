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
public class Warehouse {
    private Long id;
    private String location;
    private Long companyId;
    private List<Inventory> inventories;
}
