package com.solvd.laba.delivery.staxParser;

import com.solvd.laba.delivery.staxParser.Order;
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
public class Customer {
    private Long id;
    private Long companyId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Timestamp createdAt;
    private List<Order> orders;
}
