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
    @Test
    void testToStringAndEquality() {
        AbstractFlightInfo info1 = new AbstractFlightInfo();
        info1.setAirlineName("Air");
        info1.setAirlineLogo("L");
        info1.setFromLocation("A");
        info1.setToLocation("B");
        info1.setPrice(100.0);
        info1.setFlightNumber("X1");
        info1.setDepartureTime(LocalDateTime.now().plusDays(1));
        info1.setArrivalTime(LocalDateTime.now().plusDays(1).plusHours(2));

        AbstractFlightInfo info2 = new AbstractFlightInfo();
        info2.setAirlineName("Air");
        info2.setAirlineLogo("L");
        info2.setFromLocation("A");
        info2.setToLocation("B");
        info2.setPrice(100.0);
        info2.setFlightNumber("X1");
        info2.setDepartureTime(info1.getDepartureTime());
        info2.setArrivalTime(info1.getArrivalTime());

        assertEquals(info1, info2);
        assertEquals(info1.hashCode(), info2.hashCode());
        assertTrue(info1.toString().contains("Air"));
    }

}
