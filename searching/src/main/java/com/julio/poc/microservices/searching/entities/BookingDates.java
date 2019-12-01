package com.julio.poc.microservices.searching.entities;

import java.time.LocalDate;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Version;

import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "booking_dates")
@Data
@NoArgsConstructor
public class BookingDates {

    public BookingDates(Booking booking, LocalDate date) {
        this.booking = booking;
        this.date = date;
    }

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_booking")
    private Booking booking;


    @Column(name = "date")
    private LocalDate date;

    // Using to create an Optimistic Lock in Database
    // https://vladmihalcea.com/jpa-entity-version-property-hibernate/
    @Version
    private Integer version;

    @PrePersist
    protected void onCreate() {
        id = UUID.randomUUID();
    }
}
