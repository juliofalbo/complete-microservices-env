package com.julio.poc.microservices.frontend.clients;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import com.julio.poc.microservices.frontend.config.FeignConfiguration;
import com.julio.poc.microservices.frontend.dtos.BookingGetDTO;
import com.julio.poc.microservices.frontend.dtos.RoomGetDTO;

@FeignClient(name = "searching-service", configuration = FeignConfiguration.class)
@RibbonClient(name = "searching-service", configuration = FeignConfiguration.class)
public interface SearchingClient {

    @GetMapping("/bookings")
    Page<BookingGetDTO> getBookings(
            @RequestParam("page") int page,
            @RequestParam("size") int size,
            @RequestParam("idRoom") UUID idRoom,
            @RequestParam("guestEmail") String guestEmail,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam("startDate") LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam("endDate") LocalDate endDate
    );

    @GetMapping("/rooms/{id}")
    RoomGetDTO getRoomById(@PathVariable("id") UUID id);

    @GetMapping("/rooms")
    Page<RoomGetDTO> getRooms(@RequestParam("page") int page);

    @GetMapping("/rooms/available")
    List<RoomGetDTO> getAvailableRooms(
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "startDate") LocalDate startDate,
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            @RequestParam(value = "endDate") LocalDate endDate,
            @RequestParam(value = "id", required = false) UUID id
    );
}
