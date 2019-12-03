package com.julio.poc.microservices.frontend.clients;

import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.julio.poc.microservices.frontend.config.FeignConfiguration;
import com.julio.poc.microservices.frontend.dtos.BookingPostDTO;
import com.julio.poc.microservices.frontend.dtos.RoomPostDTO;

@FeignClient(name = "booking-service", configuration = FeignConfiguration.class)
@RibbonClient(name = "booking-service", configuration = FeignConfiguration.class)
public interface BookingClient {

    @PostMapping("/bookings")
    ResponseEntity<Void> saveBooking(@RequestBody BookingPostDTO bookingPostDTO);

    @PostMapping("/rooms")
    ResponseEntity<Void> saveRoom(@RequestBody RoomPostDTO roomPostDTO);

}
