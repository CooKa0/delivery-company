package com.solvd.laba.delivery.jaxbParser;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlAccessorType(XmlAccessType.FIELD)
public class Vehicle {

    @XmlElement
    private Long id;

    @XmlElement
    private Long companyId;

    @XmlElement
    private String licensePlate;

    @XmlElement
    private String vehicleType;

    @XmlElement
    private Double capacity;
}
