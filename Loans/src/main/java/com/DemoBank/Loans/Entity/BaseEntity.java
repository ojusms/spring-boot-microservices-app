package com.DemoBank.Loans.Entity;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;

@MappedSuperclass
@Getter @Setter @ToString
public class BaseEntity {

    @Column(updatable = false)
    @CreatedBy
    public String createdBy;

    @Column(updatable = false)
    @CreatedDate
    public LocalDateTime createdAt;

    @Column(insertable = false)
    @LastModifiedBy
    public String updatedBy;

    @Column(insertable = false)
    @LastModifiedDate
    public LocalDateTime updatedAt;
}
