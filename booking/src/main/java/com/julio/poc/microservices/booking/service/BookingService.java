package com.julio.poc.microservices.booking.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import com.julio.poc.microservices.booking.annotations.TrackMethod;
import com.julio.poc.microservices.booking.dtos.BookingGetDTO;
import com.julio.poc.microservices.booking.dtos.BookingPostDTO;
import com.julio.poc.microservices.booking.entities.Booking;
import com.julio.poc.microservices.booking.entities.BookingDates;
import com.julio.poc.microservices.booking.exception.ValidationException;
import com.julio.poc.microservices.booking.mappers.BookingMapper;
import com.julio.poc.microservices.booking.repositories.BookingDatesRepository;
import com.julio.poc.microservices.booking.repositories.BookingRepository;
import lombok.NonNull;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingService {

    private final BookingRepository repository;
    private final BookingMapper mapper;
    private final RoomService roomService;
    private final BookingDatesRepository bookingDatesRepository;
    private final EventBookingCreatedPublisher eventBookingCreatedPublisher;

    public BookingService(BookingRepository repository,
                          BookingMapper mapper,
                          RoomService roomService,
                          BookingDatesRepository bookingDatesRepository,
                          EventBookingCreatedPublisher eventBookingCreatedPublisher) {
        this.repository = repository;
        this.mapper = mapper;
        this.roomService = roomService;
        this.bookingDatesRepository = bookingDatesRepository;
        this.eventBookingCreatedPublisher = eventBookingCreatedPublisher;
    }

    @TrackMethod
    // I'm using the SERIALIZABLE isolation level because I want to performs all transactions in a sequence even in multi-instances environment.
    // It means that here I'm creating a read, range and write locking in database.
    // Drawbacks: So, now we are not allowing transaction concurrency and consequently introduce a performance penalty.
    // I choose this solution because for this scenario hotel booking, the data consistency is more important than performance.
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public Booking save(@NonNull final BookingPostDTO bookingDTO) {
        Booking newBooking = mapper.toEntity(bookingDTO);
        List<BookingDates> bookingDates = getBookingDates(bookingDTO, newBooking);
        validate(bookingDTO, bookingDates);
        enrichEntity(newBooking, bookingDates);
        return repository.save(newBooking);
    }

    public void sendEventBookingCreated(@NonNull Booking booking){
        eventBookingCreatedPublisher.sendEvent(booking.getId());
    }

    private void validate(@NonNull final BookingPostDTO bookingDTO, @NonNull final List<BookingDates> bookingDates) {
        verifyIfRoomExists(bookingDTO);
        verifyAvailability(bookingDTO, bookingDates);
    }

    private void verifyIfRoomExists(@NonNull final BookingPostDTO bookingDTO) {
        getRoom(bookingDTO);
    }

    private void verifyAvailability(@NonNull final BookingPostDTO bookingDTO,
                                    @NonNull final  List<BookingDates> bookingDates) {
        List<BookingDates> bookingsByDatesAndRoom = bookingDatesRepository
                .findBookingsByDatesAndRoom(bookingDTO.getIdRoom(), bookingDates.stream()
                .map(BookingDates::getDate).collect(Collectors.toList()));

        if(!bookingsByDatesAndRoom.isEmpty()){
            log.warn("Returning BAD REQUEST response for {} request due Room {} is not available for period {} - {}",
                    bookingDTO.getGuestEmail(),
                    bookingDTO.getIdRoom(),
                    bookingDTO.getStartDate(),
                    bookingDTO.getEndDate());
            throw new ValidationException(String
                    .format("It is not possible to booking the room %s in the period %s - %s since the room is unavailable",
                            bookingDTO.getIdRoom(),
                            bookingDTO.getStartDate(),
                            bookingDTO.getEndDate()
                    ));

        }
    }

    private void enrichEntity(@NonNull final Booking newBooking, @NonNull final List<BookingDates> bookingDates) {
        newBooking.setDates(bookingDates);
    }

    private List<BookingDates> getBookingDates(@NonNull final BookingPostDTO bookingDTO, @NonNull final Booking newBooking) {
        if(bookingDTO.getStartDate().equals(bookingDTO.getEndDate())){
            return Collections.singletonList(new BookingDates(newBooking, bookingDTO.getStartDate()));
        }

        // Automatic validating if the endDate is before startDate
        return bookingDTO.getStartDate().datesUntil(bookingDTO.getEndDate())
                .map(date -> new BookingDates(newBooking, date))
                .collect(Collectors.toList());
    }

    private void getRoom(@NonNull final BookingPostDTO bookingDTO) {
        // Automatic validating if the Room exists
        roomService.findById(bookingDTO.getIdRoom());
    }

}
