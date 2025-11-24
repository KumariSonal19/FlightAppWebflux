package com.flightapp.model.entity;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class FlightEntityTest {

    @Test
    void testFlightAllFields() {
        LocalDateTime dep = LocalDateTime.now().plusDays(1);
        LocalDateTime arr = dep.plusHours(2);

        Flight flight = new Flight();
        flight.setId("F1");
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
    void testAllArgsEqualsHashCodeToString() {
        LocalDateTime dep = LocalDateTime.now().plusDays(1);
        LocalDateTime arr = dep.plusHours(2);

        Flight f1 = new Flight(
                "F1",
                "Indigo",
                "https://logo.com/logo.png",
                "Delhi",
                "Goa",
                dep,
                arr,
                4500.0,
                180,
                150,
                "6E123"
        );

        Flight f2 = new Flight(
                "F1",
                "Indigo",
                "https://logo.com/logo.png",
                "Delhi",
                "Goa",
                dep,
                arr,
                4500.0,
                180,
                150,
                "6E123"
        );

        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());

        String s = f1.toString();
        assertTrue(s.contains("Indigo"));
        assertTrue(s.contains("6E123"));
    }
}
