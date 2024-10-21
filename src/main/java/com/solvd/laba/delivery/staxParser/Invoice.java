package com.solvd.laba.delivery.staxParser;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Invoice {
    private Long id;
    private double amount;
    private Timestamp invoiceDate;
}
