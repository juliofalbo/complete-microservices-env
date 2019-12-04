package com.julio.poc.microservices.searching.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.julio.poc.microservices.searching.SearchingApplication;
import com.julio.poc.microservices.searching.dtos.BookingGetDTO;
import com.julio.poc.microservices.searching.entities.Booking;
import com.julio.poc.microservices.searching.entities.BookingDates;
import com.julio.poc.microservices.searching.entities.BookingStates;
import com.julio.poc.microservices.searching.entities.Room;
import com.julio.poc.microservices.searching.enums.BookingStateEnum;
import com.julio.poc.microservices.searching.repositories.BookingRepository;
import com.julio.poc.microservices.searching.repositories.RoomRepository;

@SpringBootTest(classes = SearchingApplication.class)
@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class BookingServiceTests {

    @Autowired
    private BookingRepository repository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingService bookingService;

    @Test
    public void should_return_zero_bookings() {
        Page<BookingGetDTO> all = bookingService
                .search(PageRequest.of(0, 10), null, null, null);
        Assert.assertEquals(0, all.getTotalElements());
    }

    @Test
    public void should_return_one_booking_searching_without_criteria() {
        createBooking();
        Page<BookingGetDTO> all = bookingService
                .search(PageRequest.of(0, 10), null, null, null);
        Assert.assertEquals(1, all.getTotalElements());
    }

    @Test
    public void should_return_one_booking_searching_by_room_id() {
        createBooking();
        Room room = roomRepository.save(new Room("My Room", "Desc", BigDecimal.TEN));
        createBooking(room);
        Page<BookingGetDTO> all = bookingService.search(PageRequest.of(0, 10),
                room.getId(),
                null,
                null
                );
        Assert.assertEquals(1, all.getTotalElements());
        Assert.assertEquals(2, repository.findAll().size());
    }

    @Test
    public void should_return_two_booking_searching_by_guest_email() {
        createBooking();
        createBooking();
        createBooking("test@test.com");
        Page<BookingGetDTO> all = bookingService.search(PageRequest.of(0, 10),
                null,
                "j@j.com",
                null
        );
        Assert.assertEquals(2, all.getTotalElements());
        Assert.assertEquals(3, repository.findAll().size());
    }

    @Test
    public void should_return_two_booking_searching_by_state() {
        createBooking();
        createBooking();
        createBooking("test@test.com");
        createBookingWithSingleState(BookingStateEnum.PAYMENT_APPROVED.name());
        createBookingWithSingleState(BookingStateEnum.PAYMENT_APPROVED.name());
        Page<BookingGetDTO> all = bookingService.search(PageRequest.of(0, 10),
                null,
                null,
                BookingStateEnum.PAYMENT_APPROVED.name()
        );
        Assert.assertEquals(2, all.getTotalElements());
        Assert.assertEquals(5, repository.findAll().size());
    }

    private void createBooking() {
        createBooking("j@j.com");
    }

    private void createBooking(Room room) {
        Booking booking = new Booking();
        createBooking(booking,
                room,
                "j@j.com",
                Collections.singletonList(new BookingDates(booking, LocalDate.now())),
                Collections.singletonList(new BookingStates(booking, BookingStateEnum.PAYMENT_PENDING.name()))
        );
    }

    private void createBookingWithSingleState(String state) {
        Booking booking = new Booking();
        Room room = roomRepository.save(new Room("My Room", "Desc", BigDecimal.TEN));
        createBooking(booking,
                room,
                "j@j.com",
                Collections.singletonList(new BookingDates(booking, LocalDate.now())),
                Collections.singletonList(new BookingStates(booking, state))
        );
    }

    private void createBooking(String email) {
        Booking booking = new Booking();
        Room room = roomRepository.save(new Room("My Room", "Desc", BigDecimal.TEN));
        createBooking(booking,
                room,
                email,
                Collections.singletonList(new BookingDates(booking, LocalDate.now())),
                Collections.singletonList(new BookingStates(booking, BookingStateEnum.PAYMENT_PENDING.name()))
        );
    }

    private void createBooking(Booking booking,
                               Room room,
                               String email,
                               List<BookingDates> dates,
                               List<BookingStates> states) {
        booking.setRoom(room);
        booking.setGuestEmail(email);
        booking.setDates(dates);
        booking.setStates(states);
        repository.save(booking);
    }

}