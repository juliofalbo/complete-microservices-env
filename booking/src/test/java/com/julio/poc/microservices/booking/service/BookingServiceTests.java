package com.julio.poc.microservices.booking.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.julio.poc.microservices.booking.BookingApplication;
import com.julio.poc.microservices.booking.dtos.BookingPostDTO;
import com.julio.poc.microservices.booking.entities.Booking;
import com.julio.poc.microservices.booking.entities.BookingDates;
import com.julio.poc.microservices.booking.entities.BookingStates;
import com.julio.poc.microservices.booking.entities.Room;
import com.julio.poc.microservices.booking.enums.BookingStateEnum;
import com.julio.poc.microservices.booking.exception.ValidationException;
import com.julio.poc.microservices.booking.repositories.BookingRepository;
import com.julio.poc.microservices.booking.repositories.RoomRepository;
import com.tradeshift.amqp.rabbit.handlers.RabbitTemplateHandler;

@SpringBootTest(classes = BookingApplication.class)
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
    public void should_return_zero_bookings() throws InterruptedException {
        Room room = roomRepository.save(new Room("My Room", "Desc", BigDecimal.TEN));
        bookingService.save(BookingPostDTO.builder()
                .idRoom(room.getId().toString())
                .startDate(LocalDate.now())
                .endDate(LocalDate.now().plusDays(2))
                .guestEmail("j@j.com")
                .ccvEncrypted("fasdfsafasgas")
                .creditCardNumberEncrypted("asfasfsafgas")
                .expireDateEncrypted("safsafsafasgfas")
                .build(),
                room.getId().toString()
        );
        Assert.assertEquals(1, repository.findAll().size());
    }

    @Test(expected = ValidationException.class)
    public void should_return_an_exception_since_the_room_is_unavailable() {
        Room room = roomRepository.save(new Room("My Room", "Desc", BigDecimal.TEN));
        bookingService.save(BookingPostDTO.builder()
                        .idRoom(room.getId().toString())
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusDays(2))
                        .guestEmail("j@j.com")
                        .ccvEncrypted("fasdfsafasgas")
                        .creditCardNumberEncrypted("asfasfsafgas")
                        .expireDateEncrypted("safsafsafasgfas")
                        .build(),
                room.getId().toString()
        );

        bookingService.save(BookingPostDTO.builder()
                        .idRoom(room.getId().toString())
                        .startDate(LocalDate.now())
                        .endDate(LocalDate.now().plusDays(2))
                        .guestEmail("j@j.com")
                        .ccvEncrypted("fasdfsafasgas")
                        .creditCardNumberEncrypted("asfasfsafgas")
                        .expireDateEncrypted("safsafsafasgfas")
                        .build(),
                room.getId().toString()
        );
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