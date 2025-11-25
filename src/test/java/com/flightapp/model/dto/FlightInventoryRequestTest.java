package com.flightapp.model.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FlightInventoryRequestTest {

    @Test
    void testRequestFields() {
        FlightInventoryRequest req = new FlightInventoryRequest();
        req.setAirlineName("Indigo");
        req.setAirlineLogo("logo.png");
        req.setFromLocation("Delhi");
        req.setToLocation("Mumbai");
        req.setPrice(5000.0);
        req.setTotalSeats(100);
        req.setFlightNumber("6E123");

        assertEquals("Indigo", req.getAirlineName());
        assertEquals("logo.png", req.getAirlineLogo());
        assertEquals(100, req.getTotalSeats());
        assertEquals("6E123", req.getFlightNumber());
    }
}
