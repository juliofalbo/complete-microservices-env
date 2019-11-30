package com.julio.poc.microservices.booking.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.julio.poc.microservices.booking.entities.Booking;
import com.julio.poc.microservices.booking.vos.BookingIdentity;

public interface BookingRepository extends JpaRepository<Booking, BookingIdentity> {

}
