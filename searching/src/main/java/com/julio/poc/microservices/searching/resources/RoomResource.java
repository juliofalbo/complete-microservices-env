package com.julio.poc.microservices.searching.resources;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.julio.poc.microservices.searching.dtos.RoomGetDTO;
import com.julio.poc.microservices.searching.entities.Room;
import com.julio.poc.microservices.searching.service.RoomService;
import com.julio.poc.microservices.searching.specifications.builder.SpecificationBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/rooms")
public class RoomResource {

    private final RoomService service;

    public RoomResource(RoomService service) {
        this.service = service;
    }

    @GetMapping
    public Page<RoomGetDTO> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                    @RequestParam(value = "description", required = false) String description,
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                    @RequestParam(value = "creationDate", required = false) LocalDateTime startDate,
                                    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
                                    @RequestParam(value = "lastUpdate", required = false) LocalDateTime endDate,
                                    @RequestParam(value = "perNightValue", required = false) BigDecimal perNightValue
    ) {

        Specification<Room> objectSpecification = SpecificationBuilder.init()
                .withLikeFilter("description", description)
                .withEqualFilter("creationDate", startDate)
                .withEqualFilter("lastUpdate", endDate)
                .withEqualFilter("perNightValue", perNightValue)
                .buildSpec();

        return service.search(PageRequest.of(page, size), objectSpecification);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RoomGetDTO> findById(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/{id}/dates")
    public ResponseEntity<List<LocalDate>> findUnavailableDates(@PathVariable("id") UUID id) {
        return ResponseEntity.ok(service.findUnavailableDates(id));
    }

}
