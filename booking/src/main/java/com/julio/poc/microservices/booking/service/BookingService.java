package com.julio.poc.microservices.booking.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.julio.poc.microservices.booking.annotations.TrackMethod;
import com.julio.poc.microservices.booking.clients.SearchingClient;
import com.julio.poc.microservices.booking.dtos.BookingGetDTO;
import com.julio.poc.microservices.booking.dtos.BookingPostDTO;
import com.julio.poc.microservices.booking.dtos.RoomGetDTO;
import com.julio.poc.microservices.booking.exception.ConflictElementException;
import com.julio.poc.microservices.booking.mappers.BookingMapper;
import com.julio.poc.microservices.booking.repositories.BookingRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingService {

    private final BookingRepository repository;
    private final BookingMapper mapper;
    private final SearchingClient searchingClient;

    public BookingService(BookingRepository repository, BookingMapper mapper, SearchingClient searchingClient) {
        this.repository = repository;
        this.mapper = mapper;
        this.searchingClient = searchingClient;
    }

    @TrackMethod
    @Transactional
    public BookingGetDTO save(@NonNull final BookingPostDTO bookingDTO) throws NoSuchMethodException {
        verifyIfTheRoomsExists(bookingDTO);
        validateRoomAvailability(bookingDTO);
        return mapper.toDTO(repository.save(mapper.toEntity(bookingDTO)));
    }

    private void verifyIfTheRoomsExists(BookingPostDTO bookingDTO) throws NoSuchMethodException {
        Optional.ofNullable(searchingClient.getRoomById(bookingDTO.getIdRoom()))
                .orElseThrow(() -> new NoSuchMethodException("No Room found for id " + bookingDTO.getIdRoom()));
    }

    private void validateRoomAvailability(@NonNull BookingPostDTO bookingDTO) {
        Page<BookingGetDTO> bookings = searchingClient.getBookings(PageRequest.of(0, 10),
                bookingDTO.getIdRoom(),
                bookingDTO.getGuestEmail(),
                bookingDTO.getStartDate(),
                bookingDTO.getEndDate()
        );

        if (bookings.getContent().stream().findFirst().isPresent()) {
            log.warn("Returning CONFLICT response for {} request due Room {} is not available for period {} - {}",
                    bookingDTO.getGuestEmail(),
                    bookingDTO.getIdRoom(),
                    bookingDTO.getStartDate(),
                    bookingDTO.getEndDate());
            throw new ConflictElementException(String
                    .format("It is not possible to booking the room %s in the period %s - %s since the room is unavailable",
                            bookingDTO.getIdRoom(),
                            bookingDTO.getStartDate(),
                            bookingDTO.getEndDate()
                    ));
        }
    }

}
