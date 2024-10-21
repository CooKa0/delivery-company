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
@XmlAccessorType(XmlAccessType.FIELD)
public class Customer {

    @XmlElement
    private Long id;

    @XmlElement
    private Long companyId;

    @XmlElement
    private String firstName;

    @XmlElement
    private String lastName;

    @XmlElement
    private String email;

    @XmlElement
    private String phoneNumber;

    @XmlElement
    private Date createdAt;

    @XmlElementWrapper(name = "orders")
    @XmlElement(name = "order")
    private List<Order> orders;
}
