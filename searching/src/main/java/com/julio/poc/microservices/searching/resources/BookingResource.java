package com.julio.poc.microservices.searching.resources;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.julio.poc.microservices.searching.dtos.BookingGetDTO;
import com.julio.poc.microservices.searching.entities.Booking;
import com.julio.poc.microservices.searching.service.BookingService;
import com.julio.poc.microservices.searching.specifications.builder.SpecificationBuilder;
import com.julio.poc.microservices.searching.specifications.domain.FieldIn;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/bookings")
public class BookingResource {

    private final BookingService service;

    public BookingResource(BookingService service) {
        this.service = service;
    }

    @GetMapping
    public Page<BookingGetDTO> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                       @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                       @RequestParam(value = "idRoom", required = false) UUID idRoom,
                                       @RequestParam(value = "guestEmail", required = false) String guestEmail,
                                       @RequestParam(value = "state", required = false) String state,
                                       @RequestParam(value = "creationDate", required = false) LocalDateTime creationDate,
                                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                       @RequestParam(value = "lastUpdate", required = false) LocalDateTime lastUpdate
                              ){
        Specification<Booking> objectSpecification = SpecificationBuilder.init()
                .withEqualInSubclassFilter(new FieldIn("room", "id"), idRoom)
                .withEqualInListFieldFilter(new FieldIn("states", "state"), state)
                .withEqualFilter("guestEmail", guestEmail)
                .withEqualFilter("creationDate", creationDate)
                .withEqualFilter("lastUpdate", lastUpdate)
                .buildSpec();

        return service.search(PageRequest.of(page, size), objectSpecification);
    }

}
