package com.solvd.laba.delivery.jaxbParser;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class Order {

    @XmlElement
    private Long id;

    @XmlElement
    private Long customerId;

    @XmlElement
    private int quantity;

    @XmlElement
    private double totalPrice;

    @XmlElement
    private Date orderDate;

    @XmlElement
    private Invoice invoice;
}
