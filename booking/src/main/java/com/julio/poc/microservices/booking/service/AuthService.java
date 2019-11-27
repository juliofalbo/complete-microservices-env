package com.julio.poc.microservices.booking.service;

import com.julio.poc.microservices.booking.clients.AuthClient;
import com.julio.poc.microservices.booking.dtos.ApplicationDTO;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    private final AuthClient authClient;

    @Value("${spring.application.name}")
    private String applicationName;

    public AuthService(AuthClient authClient) {
        this.authClient = authClient;
    }

    public String auth(){
        return authClient.getToken(new ApplicationDTO(applicationName)).getToken();
    }

}
