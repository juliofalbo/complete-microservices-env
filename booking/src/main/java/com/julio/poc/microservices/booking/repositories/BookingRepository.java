package com.julio.poc.microservices.booking.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.julio.poc.microservices.booking.entities.Booking;

public interface BookingRepository extends JpaRepository<Booking, UUID> {

}
