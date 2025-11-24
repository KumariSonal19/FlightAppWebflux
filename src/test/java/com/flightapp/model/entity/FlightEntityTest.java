package com.flightapp.model.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class FlightEntityTest {

    @Test
    void testFlightAllFields() {
        LocalDateTime dep = LocalDateTime.now().plusDays(1);
        LocalDateTime arr = dep.plusHours(2);

        Flight flight = createFlight("F1", dep, arr);

        assertEquals("F1", flight.getId());
        assertEquals("Indigo", flight.getAirlineName());
        assertEquals("https://logo.com/img.png", flight.getAirlineLogo());
        assertEquals("Delhi", flight.getFromLocation());
        assertEquals("Goa", flight.getToLocation());
        assertEquals(dep, flight.getDepartureTime());
        assertEquals(arr, flight.getArrivalTime());
        assertEquals(4500.0, flight.getPrice());
        assertEquals(180, flight.getTotalSeats());
        assertEquals(150, flight.getAvailableSeats());
        assertEquals("6E123", flight.getFlightNumber());
    }

    @Test
    void testToStringContainsImportantFields() {
        LocalDateTime dep = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime arr = dep.plusHours(2);

        Flight flight = new Flight();
        flight.setId("F1");
        flight.setAirlineName("Indigo");
        flight.setAirlineLogo("https://logo.com/logo.png");
        flight.setFromLocation("Delhi");
        flight.setToLocation("Goa");
        flight.setDepartureTime(dep);
        flight.setArrivalTime(arr);
        flight.setPrice(4500.0);
        flight.setTotalSeats(180);
        flight.setAvailableSeats(150);
        flight.setFlightNumber("6E123");

        String s = flight.toString();
        assertTrue(s.contains("Indigo"));
        assertTrue(s.contains("6E123"));
    }

    private Flight createFlight(String id, LocalDateTime dep, LocalDateTime arr) {
        Flight flight = new Flight();
        flight.setId(id);
        flight.setAirlineName("Indigo");
        flight.setAirlineLogo("https://logo.com/img.png");
        flight.setFromLocation("Delhi");
        flight.setToLocation("Goa");
        flight.setDepartureTime(dep);
        flight.setArrivalTime(arr);
        flight.setPrice(4500.0);
        flight.setTotalSeats(180);
        flight.setAvailableSeats(150);
        flight.setFlightNumber("6E123");
        return flight;
    }
}
