package com.demobank.accounts.DTO;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;
import java.util.Map;

/*
Creating a POJO class for mapping fields to values having 'accounts' prefix in application.yml file.
Record is a type of class introduced in Java 17 that is meant to store info which cannot be altered after initialization.
It has only getter methods and no setter methods. Passing field names and types as parameters is enough,
Java compiler automatically created private final fields with the same names and getter methods without 'get' prefix.
 */
@ConfigurationProperties(prefix = "accounts")
public record AccountsContactInfoDTO(String message, Map<String, String> contactInfo, List<String> onCallSupport) {
}
