package com.julio.poc.microservices.searching.utils;

import java.time.LocalDate;

import org.junit.Assert;
import org.junit.Test;

public class DateUtilsTests {

    @Test
    public void should_get_days_between_two_dates(){
        Assert.assertEquals(10, DateUtils.getDatesBetween(LocalDate.now().minusDays(10), LocalDate.now()).size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void should_throw_an_exception_since_start_date_is_bigger_than_end_date(){
        DateUtils.getDatesBetween(LocalDate.now(), LocalDate.now().minusDays(10));
    }

}
