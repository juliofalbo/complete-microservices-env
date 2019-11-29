package com.julio.poc.microservices.booking.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.julio.poc.microservices.booking.annotations.TrackMethod;
import com.julio.poc.microservices.booking.dtos.BookingGetDTO;
import com.julio.poc.microservices.booking.dtos.BookingPostDTO;
import com.julio.poc.microservices.booking.entities.Booking;
import com.julio.poc.microservices.booking.mappers.BookingMapper;
import com.julio.poc.microservices.booking.repositories.BookingRepository;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookingService {

    private final BookingRepository repository;
    private final BookingMapper mapper;

    public BookingService(BookingRepository repository, BookingMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @TrackMethod
    @Transactional
    public BookingGetDTO save(@NonNull final BookingPostDTO bookingDTO) {
        return mapper.toDTO(repository.save(mapper.toEntity(bookingDTO)));
    }

    @TrackMethod
    @Transactional(readOnly = true)
    public Page<BookingGetDTO> search(@NonNull final Pageable pageable, Specification<Booking> specification) {
        return mapper.toDTO(repository.findAll(specification, pageable));
    }

}
