package com.julio.poc.microservices.frontend.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.julio.poc.microservices.frontend.service.RoomService;
import com.julio.poc.microservices.frontend.utils.Template;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class SiteController {

	private final RoomService roomService;

	@RequestMapping("/")
	public String home(Model model) {
		model.addAttribute("rooms", roomService.findAll());
		return Template.HOME;
	}

}