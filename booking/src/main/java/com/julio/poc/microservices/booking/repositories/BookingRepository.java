package com.julio.poc.microservices.booking.repositories;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;

import com.julio.poc.microservices.booking.entities.Booking;
import com.julio.poc.microservices.booking.vos.BookingIdentity;

public interface BookingRepository extends JpaRepository<Booking, BookingIdentity>, JpaSpecificationExecutor<Booking> {

    // Creating a Pessimistic Lock for this operation because I want to avoid dirty reads and non-repeatable reads
    //https://docs.spring.io/spring-data/jpa/docs/2.1.4.RELEASE/reference/html/#locking
    @Lock(LockModeType.PESSIMISTIC_READ)
    Booking findByBookingIdentity(BookingIdentity companyId);

}
