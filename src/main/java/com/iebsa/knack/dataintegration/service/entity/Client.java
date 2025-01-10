package com.iebsa.knack.dataintegration.service.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Clients")
@Getter
@Setter
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    private String industry;

    @Column(name = "street_address")
    private String streetAddress;

    @Column(name = "street_address_2")
    private String streetAddress2;

    private String city;
    private String state;

    @Column(name = "zip_code")
    private String zipCode;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String email;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "total_contract_value", precision = 15, scale = 2)
    private BigDecimal totalContractValue;

    @Column(name = "created_date", nullable = false)
    private LocalDate createdDate;

    public enum Status {
        Active,
        Inactive,
        UNKNOWN,
        Pending
    }
}
