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
public class Invoice {

    @XmlElement
    private Long id;

    @XmlElement
    private double amount;

    @XmlElement
    private Date invoiceDate;
}
