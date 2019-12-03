package com.julio.poc.microservices.searching.repositories;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.julio.poc.microservices.searching.entities.Room;

public interface RoomRepository extends JpaRepository<Room, UUID>, JpaSpecificationExecutor<Room> {

    @Query("select distinct room from Room room " +
            "left join Booking booking on room.id = booking.room.id " +
            "left join BookingDates dates on booking.id = dates.booking.id " +
            "where room.id = :roomId " +
            "and (dates.date is null or (dates.date > CURRENT_DATE " +
            "and room.id not in " +
            "(select room2.id from Room room2 " +
                "inner join Booking booking2 on room2.id = booking2.room.id " +
                "inner join BookingDates dates2 on booking2.id = dates2.booking.id " +
                "where dates2.date in :dates and dates2.date > CURRENT_DATE" +
            ")))")
    List<Room> findAvailableRooms(@Param("roomId") UUID roomId, @Param("dates") List<LocalDate> dates);

    @Query("select distinct room from Room room " +
            "left join Booking booking on room.id = booking.room.id " +
            "left join BookingDates dates on booking.id = dates.booking.id " +
            "where dates.date is null or (dates.date > CURRENT_DATE " +
            "and room.id not in " +
            "(select room2.id from Room room2 " +
                "inner join Booking booking2 on room2.id = booking2.room.id " +
                "inner join BookingDates dates2 on booking2.id = dates2.booking.id " +
                "where dates2.date in :dates and dates2.date > CURRENT_DATE" +
            "))")
    List<Room> findAvailableRooms(@Param("dates") List<LocalDate> dates);

}
