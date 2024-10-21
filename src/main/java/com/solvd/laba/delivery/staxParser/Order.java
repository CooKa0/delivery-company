package com.solvd.laba.delivery.staxParser;

import com.solvd.laba.delivery.staxParser.Invoice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Order {
    private Long id;
    private Long customerId;
    private int quantity;
    private double totalPrice;
    private Timestamp orderDate;
    private Invoice invoice;
}
