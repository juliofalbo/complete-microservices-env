package com.julio.poc.microservices.booking.resources;

import java.math.BigDecimal;
import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.julio.poc.microservices.booking.dtos.RoomGetDTO;
import com.julio.poc.microservices.booking.dtos.RoomPostDTO;
import com.julio.poc.microservices.booking.entities.Room;
import com.julio.poc.microservices.booking.service.RoomService;
import com.julio.poc.microservices.booking.specifications.builder.SpecificationBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/rooms")
public class RoomResource {

    private final RoomService service;

    public RoomResource(RoomService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody RoomPostDTO roomDTO) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(service.save(roomDTO).getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    @GetMapping
    public Page<RoomGetDTO> findAll(@RequestParam(value = "page", required = false, defaultValue = "0") int page,
                                    @RequestParam(value = "size", required = false, defaultValue = "10") int size,
                                    @RequestParam(value = "description", required = false) String description,
                                    @RequestParam(value = "creationDate", required = false) LocalDateTime startDate,
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
    public RoomGetDTO findById(@PathVariable("id") UUID id) {
        return service.findById(id);
    }

}
