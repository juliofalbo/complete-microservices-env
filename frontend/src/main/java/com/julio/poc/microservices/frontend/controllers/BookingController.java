package com.julio.poc.microservices.frontend.controllers;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.julio.poc.microservices.frontend.dtos.BookingPostDTO;
import com.julio.poc.microservices.frontend.dtos.RoomGetDTO;
import com.julio.poc.microservices.frontend.service.BookingService;
import com.julio.poc.microservices.frontend.service.RoomService;
import com.julio.poc.microservices.frontend.utils.Template;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Controller
public class BookingController {

    private final BookingService service;
    private final RoomService roomService;

    @RequestMapping("/room/{roomId}/booking/{startDate}/{endDate}")
    public String home(Model model,
                       @PathVariable("roomId") UUID roomId,
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                       @PathVariable("startDate") LocalDate startDate,
                       @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                       @PathVariable(value = "endDate") LocalDate endDate) {

        RoomGetDTO room = roomService.findById(roomId);
        model.addAttribute("room", room);
        model.addAttribute("totalValue", room.getPerNightValue().multiply(new BigDecimal(startDate.datesUntil(endDate).count())));
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("bookingPostDTO", new BookingPostDTO());

        return Template.BOOKING;
    }

    @PostMapping("/booking")
    public String save(@ModelAttribute BookingPostDTO bookingPostDTO, RedirectAttributes redirectAttributes) {
        try {
            service.save(bookingPostDTO);
            RoomGetDTO roomGetDTO = roomService.findById(bookingPostDTO.getIdRoom());
            redirectAttributes.addFlashAttribute("successMessage", String.format("Nice! Looking forward to receive you (%s) from %s to %s in room %s",
                    bookingPostDTO.getGuestEmail(),
                    bookingPostDTO.getStartDate(),
                    bookingPostDTO.getEndDate(),
                    roomGetDTO.getName()
                    ));
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("errorMessage", "Ops, sorry! Something is wrong with our services but our team already was notified!");
        }
        return Template.REDIRECT_ROOMS;
    }

}
