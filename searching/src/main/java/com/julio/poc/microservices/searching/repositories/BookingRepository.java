package com.julio.poc.microservices.searching.repositories;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Lock;

import com.julio.poc.microservices.searching.entities.Booking;
import com.julio.poc.microservices.searching.vos.BookingIdentity;

public interface BookingRepository extends JpaRepository<Booking, BookingIdentity>, JpaSpecificationExecutor<Booking> {

}
