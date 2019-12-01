package com.julio.poc.microservices.searching.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.julio.poc.microservices.searching.entities.BookingDates;

public interface BookingDatesRepository extends JpaRepository<BookingDates, UUID> {

    @Query("select dates from BookingDates dates inner join Booking booking on dates.booking.id = booking.id" +
            " where booking.room.id = :roomId")
    List<BookingDates> findBookingsByRoom(@Param("roomId") UUID roomId);

}
