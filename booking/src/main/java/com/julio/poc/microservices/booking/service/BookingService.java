package com.julio.poc.microservices.booking.service;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.github.alturkovic.lock.redis.alias.RedisLocked;
import com.julio.poc.microservices.booking.annotations.TrackMethod;
import com.julio.poc.microservices.booking.dtos.BookingPayloadDTO;
import com.julio.poc.microservices.booking.dtos.BookingPostDTO;
import com.julio.poc.microservices.financial.dtos.PaymentAnalyzedDTO;
import com.julio.poc.microservices.booking.entities.Booking;
import com.julio.poc.microservices.booking.entities.BookingDates;
import com.julio.poc.microservices.booking.entities.BookingStates;
import com.julio.poc.microservices.booking.entities.Room;
import com.julio.poc.microservices.booking.enums.BookingStateEnum;
import com.julio.poc.microservices.booking.events.publishers.EventBookingCreatedPublisher;
import com.julio.poc.microservices.booking.exception.ValidationException;
import com.julio.poc.microservices.booking.mappers.BookingMapper;
import com.julio.poc.microservices.booking.repositories.BookingDatesRepository;
import com.julio.poc.microservices.booking.repositories.BookingRepository;
import com.julio.poc.microservices.booking.repositories.BookingStatesRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository repository;
    private final BookingMapper mapper;
    private final RoomService roomService;
    private final BookingDatesRepository bookingDatesRepository;
    private final BookingStatesRepository bookingStatesRepository;
    private final EventBookingCreatedPublisher eventBookingCreatedPublisher;

    @TrackMethod
    // I'm using Redis to create a distributed lock
    // https://github.com/alturkovic/distribuidRoomted-lock
    @RedisLocked(expression = "#key")
    public Booking save(@NonNull final BookingPostDTO bookingDTO, @NonNull final String key) {
        Booking newBooking = mapper.toEntity(bookingDTO);
        Room room = getRoom(bookingDTO);
        List<BookingDates> bookingDates = getBookingDates(bookingDTO, newBooking);
        verifyAvailability(bookingDTO, bookingDates);
        enrichEntity(newBooking, bookingDates, room);
        return repository.save(newBooking);
    }

    public void sendEventBookingCreated(@NonNull final Booking booking,
                                        @NonNull final String creditCardNumberEncrypted,
                                        @NonNull final String expireDateEncrypted,
                                        @NonNull final String ccvEncrypted) {
        eventBookingCreatedPublisher
                .sendEvent(new BookingPayloadDTO(booking.getId(),
                        booking.getRoom().getPerNightValue()
                                .multiply(new BigDecimal(booking.getDates().size())),
                        creditCardNumberEncrypted,
                        expireDateEncrypted,
                        ccvEncrypted));
    }

    public Booking findById(@NonNull final UUID id){
        return repository.findById(id).orElseThrow(() -> new NoSuchElementException("Booking with id " + id + " not found."));
    }

    public void addBookingState(@NonNull final PaymentAnalyzedDTO paymentAnalyzedDTO){
        bookingStatesRepository.save(new BookingStates(findById(paymentAnalyzedDTO.getIdBooking()), paymentAnalyzedDTO.getState().name()));
    }

    private void verifyAvailability(@NonNull final BookingPostDTO bookingDTO,
                                    @NonNull final List<BookingDates> bookingDates) {
        List<BookingDates> bookingsByDatesAndRoom = bookingDatesRepository
                .findBookingsByDatesAndRoom(bookingDTO.getIdRoom(), bookingDates.stream()
                        .map(BookingDates::getDate).collect(Collectors.toList()));

        if (!bookingsByDatesAndRoom.isEmpty()) {
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

    private void enrichEntity(@NonNull final Booking newBooking, @NonNull final List<BookingDates> bookingDates, Room room) {
        newBooking.setDates(bookingDates);
        newBooking.setStates(Collections.singletonList(new BookingStates(newBooking, BookingStateEnum.PAYMENT_PENDING.name())));
        newBooking.setRoom(room);
    }

    private List<BookingDates> getBookingDates(@NonNull final BookingPostDTO bookingDTO, @NonNull final Booking newBooking) {
        if (bookingDTO.getStartDate().equals(bookingDTO.getEndDate())) {
            return Collections.singletonList(new BookingDates(newBooking, bookingDTO.getStartDate()));
        }

        // Automatic validating if the endDate is before startDate
        return bookingDTO.getStartDate().datesUntil(bookingDTO.getEndDate())
                .map(date -> new BookingDates(newBooking, date))
                .collect(Collectors.toList());
    }

    private Room getRoom(@NonNull final BookingPostDTO bookingDTO) {
        // Automatic validating if the Room exists
        return roomService.findById(bookingDTO.getIdRoom());
    }

}
