package com.julio.poc.microservices.booking.resources;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.julio.poc.microservices.booking.dtos.BookingGetDTO;
import com.julio.poc.microservices.booking.dtos.BookingPostDTO;
import com.julio.poc.microservices.booking.entities.Booking;
import com.julio.poc.microservices.booking.service.BookingService;
import com.julio.poc.microservices.booking.specifications.builder.SpecificationBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/bookings")
public class BookingResource {

    private final BookingService service;

    public BookingResource(BookingService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody BookingPostDTO bookingDTO){
        service.save(bookingDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public Page<BookingGetDTO> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                    @RequestParam(value = "idRoom", required = false) UUID idRoom,
                                    @RequestParam(value = "guestEmail", required = false) String guestEmail,
                                    @RequestParam(value = "startDate", required = false) LocalDate startDate,
                                    @RequestParam(value = "endDate", required = false) LocalDate endDate,
                                    @RequestParam(value = "creationDate", required = false) LocalDateTime creationDate,
                                    @RequestParam(value = "lastUpdate", required = false) LocalDateTime lastUpdate
                              ){
        Specification<Booking> objectSpecification = SpecificationBuilder.init()
                .withEqualFilter("idRoom", idRoom)
                .withEqualFilter("guestEmail", guestEmail)
                .withEqualFilter("startDate", startDate)
                .withEqualFilter("endDate", endDate)
                .withEqualFilter("creationDate", creationDate)
                .withEqualFilter("lastUpdate", lastUpdate)
                .buildSpec();

        return service.search(PageRequest.of(page, size), objectSpecification);
    }

}
