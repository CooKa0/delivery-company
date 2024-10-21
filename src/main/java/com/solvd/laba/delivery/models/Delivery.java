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
public class Delivery {
    private Long id;
    private Long employeeId;
    private Long companyId;
    private Timestamp deliveryDate;
}
