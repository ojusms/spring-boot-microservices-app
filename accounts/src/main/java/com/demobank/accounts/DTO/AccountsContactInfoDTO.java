package com.demobank.accounts.DTO;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/*
Creating a POJO class for mapping fields to values having 'accounts' prefix in application.yml file.
Record is a type of class introduced in Java 17 that is meant to store info which cannot be altered after initialization.
It has only getter methods and no setter methods. Passing field names and types as parameters is enough,
Java compiler automatically created private final fields with the same names and getter methods without 'get' prefix.
 */
/*
Updating record to class to use Setter methods to update fields from configserver when app is running
by making a POST call to endpoint '/actuator/refresh'
 */
@ConfigurationProperties(prefix = "accounts")
@Getter @Setter
public class AccountsContactInfoDTO {
    private String message;
    private Map<String, String> contactInfo;
    private List<String> onCallSupport;
}
