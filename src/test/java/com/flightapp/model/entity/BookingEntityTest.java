package com.flightapp.model.entity;

import com.flightapp.model.enums.BookingStatus;
import com.flightapp.model.enums.Gender;
import com.flightapp.model.enums.MealPreference;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingEntityTest {

    @Test
    void testBookingAllFields() {
        LocalDateTime now = LocalDateTime.now();
        Passenger passenger = new Passenger("John", Gender.MALE, 25, "A1", MealPreference.VEG);

        Booking booking = new Booking();
        booking.setId("1");
        booking.setPnr("PNR123");
        booking.setFlightId("FL1");
        booking.setUserName("John Doe");
        booking.setEmail("john@example.com");
        booking.setPassengers(List.of(passenger));
        booking.setTotalPrice(999.99);
        booking.setStatus(BookingStatus.CONFIRMED);
        booking.setBookingDate(now);
        booking.setJourneyDate(now.plusDays(1));
        booking.setCancellationDate(now.plusDays(2));
        booking.setCreatedAt(now.minusDays(1));
        booking.setUpdatedAt(now);
        booking.setAirlineName("Test Airline");
        booking.setFromLocation("Delhi");
        booking.setToLocation("Mumbai");
        booking.setDepartureTime(now.plusHours(3));

        assertEquals("1", booking.getId());
        assertEquals("PNR123", booking.getPnr());
        assertEquals("FL1", booking.getFlightId());
        assertEquals("John Doe", booking.getUserName());
        assertEquals("john@example.com", booking.getEmail());
        assertEquals(1, booking.getPassengers().size());
        assertEquals(999.99, booking.getTotalPrice());
        assertEquals(BookingStatus.CONFIRMED, booking.getStatus());
        assertEquals(now, booking.getBookingDate());
        assertEquals(now.plusDays(1), booking.getJourneyDate());
        assertEquals(now.plusDays(2), booking.getCancellationDate());
        assertEquals(now.minusDays(1), booking.getCreatedAt());
        assertEquals(now, booking.getUpdatedAt());
        assertEquals("Test Airline", booking.getAirlineName());
        assertEquals("Delhi", booking.getFromLocation());
        assertEquals("Mumbai", booking.getToLocation());
        assertEquals(now.plusHours(3), booking.getDepartureTime());
    }
    
    @Test
    void testAllArgsAndNoArgsConstructorsAndEqualsHashCodeToString() {
        LocalDateTime now = LocalDateTime.now();
        Passenger p = new Passenger("John", Gender.MALE, 25, "A1", MealPreference.VEG);

        Booking b1 = new Booking(
                "1",
                "PNR123",
                "FL1",
                "John Doe",
                "john@example.com",
                List.of(p),
                1000.0,
                BookingStatus.CONFIRMED,
                now,
                now.plusDays(1),
                null,
                now.minusDays(1),
                now,
                "Test Airline",
                "Delhi",
                "Mumbai",
                now.plusHours(2)
        );

        Booking b2 = new Booking();
        b2.setId("1");
        b2.setPnr("PNR123");
        b2.setFlightId("FL1");
        b2.setUserName("John Doe");
        b2.setEmail("john@example.com");
        b2.setPassengers(List.of(p));
        b2.setTotalPrice(1000.0);
        b2.setStatus(BookingStatus.CONFIRMED);
        b2.setBookingDate(now);
        b2.setJourneyDate(now.plusDays(1));
        b2.setCancellationDate(null);
        b2.setCreatedAt(now.minusDays(1));
        b2.setUpdatedAt(now);
        b2.setAirlineName("Test Airline");
        b2.setFromLocation("Delhi");
        b2.setToLocation("Mumbai");
        b2.setDepartureTime(now.plusHours(2));

        // hits equals & hashCode
        assertEquals(b1, b2);
        assertEquals(b1.hashCode(), b2.hashCode());

        // hits toString
        String s = b1.toString();
        assertTrue(s.contains("PNR123"));
        assertTrue(s.contains("Test Airline"));
    }
}
