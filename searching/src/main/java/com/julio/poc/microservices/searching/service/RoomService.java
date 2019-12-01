package com.julio.poc.microservices.searching.service;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.julio.poc.microservices.searching.annotations.TrackMethod;
import com.julio.poc.microservices.searching.dtos.RoomGetDTO;
import com.julio.poc.microservices.searching.entities.BookingDates;
import com.julio.poc.microservices.searching.entities.Room;
import com.julio.poc.microservices.searching.mappers.RoomMapper;
import com.julio.poc.microservices.searching.repositories.BookingDatesRepository;
import com.julio.poc.microservices.searching.repositories.RoomRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final RoomRepository repository;
    private final BookingDatesRepository bookingDatesRepository;
    private final RoomMapper mapper;

    @TrackMethod
    @Transactional(readOnly = true)
    public Page<RoomGetDTO> search(@NonNull final Pageable pageable, Specification<Room> specification) {
        return mapper.toDTO(repository.findAll(specification, pageable));
    }

    @TrackMethod
    @Transactional(readOnly = true)
    public RoomGetDTO findById(@NonNull UUID id) {
        return mapper.toDTO(repository.findById(id).orElseThrow(NoSuchElementException::new));
    }


    @TrackMethod
    @Transactional(readOnly = true)
    public List<LocalDate> findUnavailableDates(@NonNull UUID id) {
        return bookingDatesRepository.findBookingsByRoom(id).stream().map(BookingDates::getDate).collect(Collectors.toList());
    }
}
