package com.flightapp.model.dto;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class AbstractFlightInfoTest {

    @Test
    void testSettersAndGetters() {
        AbstractFlightInfo info = new AbstractFlightInfo();

        info.setAirlineName("Indigo");
        info.setAirlineLogo("logo.png");
        info.setFromLocation("Delhi");
        info.setToLocation("Goa");
        info.setPrice(4500.0);
        info.setFlightNumber("6E123");

        LocalDateTime dep = LocalDateTime.now().plusDays(1);
        LocalDateTime arr = dep.plusHours(2);

        info.setDepartureTime(dep);
        info.setArrivalTime(arr);

        assertEquals("Indigo", info.getAirlineName());
        assertEquals("logo.png", info.getAirlineLogo());
        assertEquals("Delhi", info.getFromLocation());
        assertEquals("Goa", info.getToLocation());
        assertEquals(4500.0, info.getPrice());
        assertEquals("6E123", info.getFlightNumber());
        assertEquals(dep, info.getDepartureTime());
        assertEquals(arr, info.getArrivalTime());
    }
}
