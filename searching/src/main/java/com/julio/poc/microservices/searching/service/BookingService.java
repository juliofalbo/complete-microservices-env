package com.julio.poc.microservices.searching.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.julio.poc.microservices.searching.annotations.TrackMethod;
import com.julio.poc.microservices.searching.dtos.BookingGetDTO;
import com.julio.poc.microservices.searching.dtos.BookingPostDTO;
import com.julio.poc.microservices.searching.entities.Booking;
import com.julio.poc.microservices.searching.mappers.BookingMapper;
import com.julio.poc.microservices.searching.repositories.BookingRepository;
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
    @Transactional(readOnly = true)
    public Page<BookingGetDTO> search(@NonNull final Pageable pageable, Specification<Booking> specification) {
        return mapper.toDTO(repository.findAll(specification, pageable));
    }

}
