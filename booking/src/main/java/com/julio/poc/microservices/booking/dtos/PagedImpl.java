package com.julio.poc.microservices.booking.dtos;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PagedImpl<T> extends PageImpl<T> {

    @JsonCreator(mode = JsonCreator.Mode.PROPERTIES)
    public PagedImpl(@JsonProperty("content") List<T> content,
                      @JsonProperty("number") int number,
                      @JsonProperty("size") int size,
                      @JsonProperty("totalElements") Long totalElements) {
        super(content, PageRequest.of(number, size), totalElements);
    }

    public PagedImpl(List<T> content, Pageable pageable, long total) {
        super(content, pageable, total);
    }

    public PagedImpl(List<T> content) {
        super(content);
    }

    public PagedImpl() {
        super(new ArrayList<T>());
    }

}