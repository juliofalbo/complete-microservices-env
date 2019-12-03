package com.julio.poc.microservices.frontend.controllers;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.julio.poc.microservices.frontend.dtos.RoomGetDTO;
import com.julio.poc.microservices.frontend.service.RoomService;
import com.julio.poc.microservices.frontend.utils.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class RoomController {

    private final RoomService service;

    @RequestMapping("/rooms")
    public String rooms(Model model) {
        model.addAttribute("rooms", Collections.emptyList());
        model.addAttribute("allRooms", service.findAll());
        return Template.ROOMS;
    }

    @RequestMapping("/rooms/available")
    public String availableRooms(Model model,
                                 @RequestParam(value = "roomId") String roomId,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                 @RequestParam("startDate") LocalDate startDate,
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                 @RequestParam(value = "endDate") LocalDate endDate) {

        if (validateDates(model, startDate, endDate)) {
            populateModels(model, roomId, startDate, endDate, Collections.emptyList());
            return Template.ROOMS;
        }

        try {
            List<RoomGetDTO> all = service.findAllAvailableRooms(startDate, endDate, roomId.equals("all") ? null : UUID.fromString(roomId));
            populateModels(model, roomId, startDate, endDate, all);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Ops, sorry! Something is wrong with our services but our team already was notified!");
            populateModels(model, roomId, startDate, endDate, Collections.emptyList());
        }
        return Template.ROOMS;
    }

    private boolean validateDates(Model model, LocalDate startDate, LocalDate endDate) {
        if (startDate.isAfter(endDate)) {
            model.addAttribute("errorMessage", "Start Date must be before End Date");
            return true;
        }
        return false;
    }

    private void populateModels(Model model, String roomId, LocalDate startDate, LocalDate endDate, List<RoomGetDTO> rooms
    ) {
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        model.addAttribute("rooms", rooms);
        model.addAttribute("allRooms", service.findAll());
        model.addAttribute("roomId", roomId);
        model.addAttribute("startDate", dateTimeFormatter.format(startDate));
        model.addAttribute("endDate", dateTimeFormatter.format(endDate));
    }

}
