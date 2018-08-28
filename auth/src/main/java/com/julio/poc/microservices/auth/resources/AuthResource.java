package com.julio.poc.microservices.auth.resources;

import com.julio.poc.microservices.auth.dto.ApplicationDTO;
import com.julio.poc.microservices.auth.dto.TokenDTO;
import com.julio.poc.microservices.auth.services.JwtTokenService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Slf4j
public class AuthResource {

    @Autowired
    private JwtTokenService service;

    @PostMapping
    public TokenDTO generateTokenJwt(@NonNull @RequestBody ApplicationDTO application) {
        return new TokenDTO(service.getToken(application.getAppName()));
    }

    @PostMapping(value = "/refresh")
    public TokenDTO refreshTokenJwt(@NonNull @RequestBody TokenDTO token) {
        return new TokenDTO(service.refreshToken(token.getToken()));
    }

    @PostMapping(value = "/claims")
    public ApplicationDTO returnApplicationNameFromToken(@NonNull @RequestBody TokenDTO token) {
        return new ApplicationDTO(service.getApplicationNameFromToken(token.getToken()));
    }

}