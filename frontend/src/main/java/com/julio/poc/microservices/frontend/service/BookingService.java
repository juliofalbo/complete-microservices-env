package com.julio.poc.microservices.frontend.service;

import org.springframework.stereotype.Service;

import com.julio.poc.microservices.frontend.clients.BookingClient;
import com.julio.poc.microservices.frontend.dtos.BookingPostDTO;
import com.julio.poc.microservices.frontend.utils.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {

    private final BookingClient bookingClient;

    public String save(BookingPostDTO bookingPostDTO){
        bookingClient.saveBooking(bookingPostDTO);
        return Template.REDIRECT_BOOKING;
    }

}
