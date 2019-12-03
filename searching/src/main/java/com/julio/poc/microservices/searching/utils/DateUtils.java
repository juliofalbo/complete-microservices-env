package com.julio.poc.microservices.searching.utils;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.NonNull;

public class DateUtils {

    public static List<LocalDate> getDatesBetween(@NonNull final LocalDate startDate, @NonNull final LocalDate endDate){
        if(startDate.equals(endDate)){
            return Collections.singletonList(startDate);
        }
        return startDate.datesUntil(endDate).collect(Collectors.toList());
    }

}
