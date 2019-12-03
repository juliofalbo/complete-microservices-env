package com.julio.poc.microservices.frontend.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import com.julio.poc.microservices.frontend.annotations.TrackMethod;
import com.julio.poc.microservices.frontend.clients.SearchingClient;
import com.julio.poc.microservices.frontend.dtos.RoomGetDTO;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class RoomService {

    private final SearchingClient searchingClient;

    @TrackMethod
    public List<RoomGetDTO> findAll() {
        Page<RoomGetDTO> response = null;
        List<RoomGetDTO> rooms = new ArrayList<>();

        do {
            int page = response == null ? 0 : response.getPageable().getPageNumber() + 1;
            response = searchingClient.getRooms(page);
            rooms.addAll(response.getContent());
        } while (!response.isLast());

        return rooms;
    }

    @TrackMethod
    public List<RoomGetDTO> findAllAvailableRooms(@NonNull final LocalDate startDate,
                                                  @NonNull final LocalDate endDate,
                                                  final UUID roomId) {
        return searchingClient.getAvailableRooms(startDate, endDate, roomId);
    }

    @TrackMethod
    public RoomGetDTO findById(final UUID roomId) {
        return searchingClient.getRoomById(roomId);
    }

}
