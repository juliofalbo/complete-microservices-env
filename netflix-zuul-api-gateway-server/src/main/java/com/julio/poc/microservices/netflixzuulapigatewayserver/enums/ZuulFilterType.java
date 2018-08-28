package com.julio.poc.microservices.netflixzuulapigatewayserver.enums;

import lombok.Getter;

@Getter
public enum ZuulFilterType {
    PRE("pre"),
    POST("post"),
    ERROR("error");

    ZuulFilterType(String value) {
        this.value = value;
    }

    private String value;

}
