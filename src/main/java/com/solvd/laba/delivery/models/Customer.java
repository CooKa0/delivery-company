package com.solvd.laba.delivery.models;

import java.sql.Timestamp;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private Long companyId;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private Timestamp createdAt;
}
