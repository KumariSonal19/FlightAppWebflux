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
    void testEqualsHashCodeToString() {
        LocalDateTime dep = LocalDateTime.of(2025, 1, 1, 10, 0);
        LocalDateTime arr = dep.plusHours(2);

        Flight f1 = new Flight();
        f1.setId("F1");
        f1.setAirlineName("Indigo");
        f1.setAirlineLogo("https://logo.com/logo.png");
        f1.setFromLocation("Delhi");
        f1.setToLocation("Goa");
        f1.setDepartureTime(dep);
        f1.setArrivalTime(arr);
        f1.setPrice(4500.0);
        f1.setTotalSeats(180);
        f1.setAvailableSeats(150);
        f1.setFlightNumber("6E123");

        Flight f2 = new Flight();
        f2.setId("F1");
        f2.setAirlineName("Indigo");
        f2.setAirlineLogo("https://logo.com/logo.png");
        f2.setFromLocation("Delhi");
        f2.setToLocation("Goa");
        f2.setDepartureTime(dep);
        f2.setArrivalTime(arr);
        f2.setPrice(4500.0);
        f2.setTotalSeats(180);
        f2.setAvailableSeats(150);
        f2.setFlightNumber("6E123");

        assertEquals(f1, f2);
        assertEquals(f1.hashCode(), f2.hashCode());

        String s = f1.toString();
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
