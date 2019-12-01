package com.julio.poc.microservices.booking.entities;

import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Version;

import com.julio.poc.microservices.booking.enums.BookingStateEnum;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking_states")
@Data
@NoArgsConstructor
public class BookingStates {

    public BookingStates(Booking booking, String state) {
        this.booking = booking;
        this.state = state;
    }

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_booking")
    private Booking booking;

    @Column(name = "state")
    private String state;

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

    public BookingStateEnum getState() {
        return BookingStateEnum.valueOf(this.state);
    }
}
