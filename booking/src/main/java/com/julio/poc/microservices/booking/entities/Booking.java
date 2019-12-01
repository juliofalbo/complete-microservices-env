package com.julio.poc.microservices.booking.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Email;

import lombok.Data;

@Entity
@Table(name = "bookings")
@Data
public class Booking {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_room")
    private Room room;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "booking"
    )
    private List<BookingDates> dates;

    @OneToMany(
            cascade = CascadeType.ALL,
            orphanRemoval = true,
            mappedBy = "booking"
    )
    private List<BookingStates> states;

    @Email
    @Column(name = "guest_email")
    private String guestEmail;

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
