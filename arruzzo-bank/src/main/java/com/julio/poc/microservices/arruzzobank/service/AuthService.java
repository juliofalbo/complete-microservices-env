package com.julio.poc.microservices.arruzzobank.service;

import com.julio.poc.microservices.arruzzobank.clients.AuthClient;
import com.julio.poc.microservices.arruzzobank.dtos.ApplicationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private AuthClient authClient;

    @Value("${spring.application.name}")
    private String applicationName;

    public String auth(){
        return authClient.getToken(new ApplicationDTO(applicationName)).getToken();
    }

}
