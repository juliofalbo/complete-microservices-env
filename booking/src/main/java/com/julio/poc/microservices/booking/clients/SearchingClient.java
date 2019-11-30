package com.julio.poc.microservices.booking.clients;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.julio.poc.microservices.booking.dtos.BookingGetDTO;
import com.julio.poc.microservices.booking.dtos.RoomGetDTO;

@FeignClient(name = "searching-service")
@RibbonClient(name = "searching-service")
public interface SearchingClient {

    @GetMapping("/bookings")
    Page<BookingGetDTO> getBookings(
            Pageable pageable,
            @RequestParam("idRoom") UUID idRoom,
            @RequestParam("guestEmail") String guestEmail,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam("startDate") LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam("endDate") LocalDate endDate
    );

    @GetMapping("/rooms/{id}")
    RoomGetDTO getRoomById(@PathVariable("id") UUID id);

}
