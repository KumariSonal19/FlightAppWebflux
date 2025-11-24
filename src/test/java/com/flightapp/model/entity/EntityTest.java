package com.flightapp.model.entity;

import com.flightapp.model.enums.BookingStatus;
import com.flightapp.model.enums.Gender;
import com.flightapp.model.enums.MealPreference;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class EntityTest {

    @Test
    void testBookingEntityFullCoverage() {
        LocalDateTime now = LocalDateTime.now();

        Passenger p = new Passenger("John", Gender.MALE, 25, "A1", MealPreference.VEG);

        Booking b = new Booking();
        b.setId("1");
        b.setPnr("PNR123");
        b.setFlightId("FLIGHT1");
        b.setUserName("John Doe");
        b.setEmail("john@example.com");
        b.setPassengers(List.of(p));
        b.setTotalPrice(123.45);
        b.setStatus(BookingStatus.CONFIRMED);
        b.setBookingDate(now);
        b.setJourneyDate(now.plusDays(2));
        b.setCancellationDate(now.plusDays(1));
        b.setCreatedAt(now);
        b.setUpdatedAt(now.plusHours(1));
        b.setAirlineName("Indigo");
        b.setFromLocation("Delhi");
        b.setToLocation("Mumbai");
        b.setDepartureTime(now.plusDays(3));

        assertEquals("1", b.getId());
        assertEquals("PNR123", b.getPnr());
        assertEquals("FLIGHT1", b.getFlightId());
        assertEquals("John Doe", b.getUserName());
        assertEquals("john@example.com", b.getEmail());
        assertEquals(1, b.getPassengers().size());
        assertEquals(123.45, b.getTotalPrice());
        assertEquals(BookingStatus.CONFIRMED, b.getStatus());
        assertEquals(now, b.getBookingDate());
        assertEquals("Indigo", b.getAirlineName());
        assertEquals("Delhi", b.getFromLocation());
        assertEquals("Mumbai", b.getToLocation());
        assertNotNull(b.getDepartureTime());
    }

    @Test
    void testFlightEntityFullCoverage() {
        LocalDateTime dep = LocalDateTime.now().plusDays(3);
        LocalDateTime arr = dep.plusHours(2);

        Flight f = new Flight();
        f.setId("10");
        f.setAirlineName("Air India");
        f.setAirlineLogo("https://example.com/logo.png");
        f.setFromLocation("Delhi");
        f.setToLocation("Pune");
        f.setDepartureTime(dep);
        f.setArrivalTime(arr);
        f.setPrice(4000.0);
        f.setTotalSeats(200);
        f.setAvailableSeats(150);
        f.setFlightNumber("AI202");

        assertEquals("10", f.getId());
        assertEquals("Air India", f.getAirlineName());
        assertEquals("https://example.com/logo.png", f.getAirlineLogo());
        assertEquals("Delhi", f.getFromLocation());
        assertEquals("Pune", f.getToLocation());
        assertEquals(dep, f.getDepartureTime());
        assertEquals(arr, f.getArrivalTime());
        assertEquals(4000.0, f.getPrice());
        assertEquals(200, f.getTotalSeats());
        assertEquals(150, f.getAvailableSeats());
        assertEquals("AI202", f.getFlightNumber());
    }

    @Test
    void testPassengerEntityFullCoverage() {
        Passenger p = new Passenger();
        p.setName("Jane");
        p.setGender(Gender.FEMALE);
        p.setAge(20);
        p.setSeatNumber("B5");
        p.setMealPreference(MealPreference.NON_VEG);

        assertEquals("Jane", p.getName());
        assertEquals(Gender.FEMALE, p.getGender());
        assertEquals(20, p.getAge());
        assertEquals("B5", p.getSeatNumber());
        assertEquals(MealPreference.NON_VEG, p.getMealPreference());
    }
}
