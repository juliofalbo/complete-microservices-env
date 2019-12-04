package com.julio.poc.microservices.searching.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.julio.poc.microservices.searching.annotations.TrackMethod;
import com.julio.poc.microservices.searching.dtos.BookingGetDTO;
import com.julio.poc.microservices.searching.entities.Booking;
import com.julio.poc.microservices.searching.mappers.BookingMapper;
import com.julio.poc.microservices.searching.repositories.BookingRepository;
import com.julio.poc.microservices.searching.specifications.builder.SpecificationBuilder;
import com.julio.poc.microservices.searching.specifications.domain.FieldIn;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class BookingService {

    private final BookingRepository repository;
    private final BookingMapper mapper;

    @TrackMethod
    @Transactional(readOnly = true)
    public Page<BookingGetDTO> search(Pageable pageable,
                                      UUID idRoom,
                                      String guestEmail,
                                      String state) {
        Specification<Booking> specification = SpecificationBuilder.init()
                .withEqualInSubclassFilter(new FieldIn("room", "id"), idRoom)
                .withEqualInListFieldFilter(new FieldIn("states", "state"), state)
                .withEqualFilter("guestEmail", guestEmail)
                .buildSpec();
        return mapper.toDTO(repository.findAll(specification, pageable));
    }

}
