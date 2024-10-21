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
public class Inventory {
    private Long id;
    private Long warehouseId;
    private Long productId;
    private int quantity;
    private Timestamp lastUpdated;
    private Long companyId;
}
