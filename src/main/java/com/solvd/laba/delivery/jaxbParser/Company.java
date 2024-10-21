package com.solvd.laba.delivery.jaxbParser;

import jakarta.xml.bind.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@XmlRootElement(name = "company")
@XmlAccessorType(XmlAccessType.FIELD)
public class Company {

    @XmlElement
    private Long id;

    @XmlElement
    private String name;

    @XmlElement
    private String location;

    @XmlElement
    private Date createdAt;

    @XmlElementWrapper(name = "customers")
    @XmlElement(name = "customer")
    private List<Customer> customers;

    @XmlElementWrapper(name = "vehicles")
    @XmlElement(name = "vehicle")
    private List<Vehicle> vehicles;
}
