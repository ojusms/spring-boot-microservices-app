package com.demobank.accounts.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

// using Lombok annotations to build Getter and Setter methods, Constructors, and ToString method.
// Not using @Data annotation because we do no want @EqualsAndHashCode annotation to be included

@Entity
@Table(name = "customer")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
public class Customer extends BaseEntity {

    @Column(name = "customer_id")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long customerId;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "mobile_number")
    private String mobileNumber;
}
