package com.solvd.laba.delivery.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Invoice {
    private Long id;
    private Long orderId;
    private Timestamp invoiceDate;
    private Double amount;
}
