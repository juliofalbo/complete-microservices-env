package com.julio.poc.microservices.booking.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.julio.poc.microservices.booking.entities.BookingDates;

public interface BookingDatesRepository extends JpaRepository<BookingDates, UUID> {

    @Query("select dates from BookingDates dates inner join Booking booking on dates.booking.id = booking.id" +
            " where booking.idRoom = :roomId and date in :dates")
    List<BookingDates> findBookingsByDatesAndRoom(@Param("roomId") UUID roomId, @Param("dates") List<LocalDate> dates);

}
