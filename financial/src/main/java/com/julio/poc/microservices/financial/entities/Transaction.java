package com.julio.poc.microservices.financial.entities;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;


import lombok.Data;

@Entity
@Table(name = "payment_transactions")
@Data
public class Transaction {

    @Id
    private UUID id;

    @Column(name = "booking_id")
    private UUID idBooking;

    @Column(name = "total_value")
    private BigDecimal totalValue;

    @Column(name = "analysis")
    private String analysis;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "last_update")
    private LocalDateTime lastUpdate;

    // Using to create an Optimistic Lock in Database
    // https://vladmihalcea.com/jpa-entity-version-property-hibernate/
    @Version
    private Integer version;

    @PrePersist
    protected void onCreate() {
        id = UUID.randomUUID();
        creationDate = LocalDateTime.now();
        lastUpdate = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        lastUpdate = LocalDateTime.now();
    }

}
