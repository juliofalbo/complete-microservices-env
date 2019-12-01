package com.julio.poc.microservices.booking.resources;

import java.net.URI;
import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.julio.poc.microservices.booking.dtos.RoomPostDTO;
import com.julio.poc.microservices.booking.service.RoomService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/rooms")
public class RoomResource {

    private final RoomService service;

    @PostMapping
    public ResponseEntity<Void> save(@Valid @RequestBody RoomPostDTO roomDTO) {
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(service.save(roomDTO).getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

}
