package com.julio.poc.microservices.searching.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

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
import com.julio.poc.microservices.searching.dtos.RoomGetDTO;
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
public class RoomServiceTests {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private RoomService roomService;

    @Test
    public void should_return_zero_rooms() {
        Page<RoomGetDTO> all = roomService
                .search(PageRequest.of(0, 10), null, null, null);
        Assert.assertEquals(0, all.getTotalElements());
    }

    @Test
    public void should_return_one_room_searching_without_criteria() {
        roomRepository.save(new Room("My Room", "Desc", BigDecimal.TEN));
        Page<RoomGetDTO> all = roomService
                .search(PageRequest.of(0, 10), null, null, null);
        Assert.assertEquals(1, all.getTotalElements());
    }

    @Test
    public void should_return_one_room_searching_by_room_id() {
        roomRepository.save(new Room("My Room", "Desc", BigDecimal.TEN));
        roomRepository.save(new Room("Another Room", "Desc", BigDecimal.TEN));
        Room room = roomRepository.save(new Room("My Room", "Desc", BigDecimal.TEN));
        RoomGetDTO byId = roomService.findById(room.getId());
        Assert.assertNotNull(byId);
        Assert.assertEquals(room.getId(), byId.getId());
    }

    @Test(expected = NoSuchElementException.class)
    public void should_throw_exception_when_searching_by_room_invalid_id() {
        RoomGetDTO byId = roomService.findById(UUID.randomUUID());
    }

    @Test
    public void should_return_one_room_searching_by_room_name() {
        roomRepository.save(new Room("My Room", "Desc", BigDecimal.TEN));
        roomRepository.save(new Room("Another Room", "Desc", BigDecimal.TEN));
        roomRepository.save(new Room("Another Room 2", "Desc", BigDecimal.TEN));
        Page<RoomGetDTO> byName = roomService.search(PageRequest.of(0, 10),
                "My Room",
                null,
                null
        );
        Assert.assertEquals(1, byName.getTotalElements());
        Assert.assertEquals(3, roomRepository.findAll().size());
    }

    @Test
    public void should_return_three_rooms_searching_by_room_desc_as_like() {
        roomRepository.save(new Room("My Room", "Desc 1", BigDecimal.TEN));
        roomRepository.save(new Room("Another Room", "Desc 2", BigDecimal.TEN));
        roomRepository.save(new Room("Another Room 2", "Desc 3", BigDecimal.TEN));
        roomRepository.save(new Room("My Room", "Aaaaa", BigDecimal.TEN));
        Page<RoomGetDTO> byDesc = roomService.search(PageRequest.of(0, 10),
                null,
                "Desc",
                null
        );
        Assert.assertEquals(3, byDesc.getTotalElements());
        Assert.assertEquals(4, roomRepository.findAll().size());
    }

    @Test
    public void should_return_one_rooms_searching_by_room_value() {
        roomRepository.save(new Room("My Room", "Desc 1", BigDecimal.ONE));
        roomRepository.save(new Room("Another Room", "Desc 2", BigDecimal.TEN));
        roomRepository.save(new Room("Another Room 2", "Desc 3", BigDecimal.TEN));
        roomRepository.save(new Room("My Room", "Aaaaa", BigDecimal.TEN));
        Page<RoomGetDTO> byDesc = roomService.search(PageRequest.of(0, 10),
                null,
                null,
                BigDecimal.ONE
        );
        Assert.assertEquals(1, byDesc.getTotalElements());
        Assert.assertEquals(4, roomRepository.findAll().size());
    }

    @Test
    public void should_return_one_available_room_with_id() {
        Room myRoom = roomRepository.save(new Room("My Room", "Desc 1", BigDecimal.ONE));
        Room anotherRoom = roomRepository.save(new Room("Another Room", "Desc 2", BigDecimal.TEN));

        createBooking(myRoom, LocalDate.now());
        createBooking(myRoom, LocalDate.now().plusDays(1));
        createBooking(myRoom, LocalDate.now().plusDays(2));

        createBooking(anotherRoom, LocalDate.now());
        createBooking(anotherRoom, LocalDate.now().plusDays(10));
        createBooking(anotherRoom, LocalDate.now().plusDays(11));

        List<RoomGetDTO> rooms = roomService.findAvailableRooms(myRoom.getId(), LocalDate.now().plusMonths(1), LocalDate.now().plusMonths(1).plusDays(2));
        Assert.assertEquals(1, rooms.size());
        Assert.assertEquals(myRoom.getId(), rooms.get(0).getId());
        Assert.assertEquals(2, roomRepository.findAll().size());
    }

    @Test
    public void should_return_zero_available_room_with_id() {
        Room myRoom = roomRepository.save(new Room("My Room", "Desc 1", BigDecimal.ONE));
        Room anotherRoom = roomRepository.save(new Room("Another Room", "Desc 2", BigDecimal.TEN));

        createBooking(myRoom, LocalDate.now());
        createBooking(myRoom, LocalDate.now().plusDays(1));
        createBooking(myRoom, LocalDate.now().plusDays(2));

        createBooking(anotherRoom, LocalDate.now());
        createBooking(anotherRoom, LocalDate.now().plusDays(10));
        createBooking(anotherRoom, LocalDate.now().plusDays(11));

        List<RoomGetDTO> rooms = roomService.findAvailableRooms(myRoom.getId(), LocalDate.now().plusDays(2), LocalDate.now().plusMonths(1).plusDays(2));
        Assert.assertEquals(0, rooms.size());
        Assert.assertEquals(2, roomRepository.findAll().size());
    }

    @Test
    public void should_return_one_available_room() {
        Room myRoom = roomRepository.save(new Room("My Room", "Desc 1", BigDecimal.ONE));
        Room anotherRoom = roomRepository.save(new Room("Another Room", "Desc 2", BigDecimal.TEN));

        createBooking(myRoom, LocalDate.now());
        createBooking(myRoom, LocalDate.now().plusDays(1));
        createBooking(myRoom, LocalDate.now().plusDays(2));

        createBooking(anotherRoom, LocalDate.now());
        createBooking(anotherRoom, LocalDate.now().plusDays(10));
        createBooking(anotherRoom, LocalDate.now().plusDays(11));

        List<RoomGetDTO> rooms = roomService.findAvailableRooms(null, LocalDate.now().plusDays(2), LocalDate.now().plusDays(3));
        Assert.assertEquals(1, rooms.size());
        Assert.assertEquals(anotherRoom.getId(), rooms.get(0).getId());
        Assert.assertEquals(2, roomRepository.findAll().size());
    }

    @Test
    public void should_return_zero_available_room() {
        Room myRoom = roomRepository.save(new Room("My Room", "Desc 1", BigDecimal.ONE));
        Room anotherRoom = roomRepository.save(new Room("Another Room", "Desc 2", BigDecimal.TEN));

        createBooking(myRoom, LocalDate.now());
        createBooking(myRoom, LocalDate.now().plusDays(1));
        createBooking(myRoom, LocalDate.now().plusDays(2));

        createBooking(anotherRoom, LocalDate.now());
        createBooking(anotherRoom, LocalDate.now().plusDays(10));
        createBooking(anotherRoom, LocalDate.now().plusDays(11));

        List<RoomGetDTO> rooms = roomService.findAvailableRooms(null, LocalDate.now().plusDays(2), LocalDate.now().plusMonths(1).plusDays(2));
        Assert.assertEquals(0, rooms.size());
        Assert.assertEquals(2, roomRepository.findAll().size());
    }

    @Test
    public void should_return_unavailable_dates_for_rooms() {
        Room myRoom = roomRepository.save(new Room("My Room", "Desc 1", BigDecimal.ONE));
        Room anotherRoom = roomRepository.save(new Room("Another Room", "Desc 2", BigDecimal.TEN));

        createBooking(myRoom, LocalDate.now());
        createBooking(myRoom, LocalDate.now().plusDays(1));
        createBooking(myRoom, LocalDate.now().plusDays(2));
        createBooking(myRoom, LocalDate.now().plusDays(3));
        createBooking(myRoom, LocalDate.now().plusDays(4));

        createBooking(anotherRoom, LocalDate.now());
        createBooking(anotherRoom, LocalDate.now().plusDays(10));
        createBooking(anotherRoom, LocalDate.now().plusDays(11));

        List<LocalDate> room1 = roomService.findUnavailableDates(myRoom.getId());
        List<LocalDate> room2 = roomService.findUnavailableDates(anotherRoom.getId());
        Assert.assertEquals(5, room1.size());
        Assert.assertEquals(3, room2.size());
        Assert.assertEquals(2, roomRepository.findAll().size());
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

    private void createBooking(Room room, LocalDate date) {
        Booking booking = new Booking();
        createBooking(booking,
                room,
                "j@j.com",
                Collections.singletonList(new BookingDates(booking, date)),
                Collections.singletonList(new BookingStates(booking, BookingStateEnum.PAYMENT_PENDING.name()))
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
        bookingRepository.save(booking);
    }

}