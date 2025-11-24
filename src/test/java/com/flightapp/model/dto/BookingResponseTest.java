package com.flightapp.model.dto;

import com.flightapp.model.entity.Passenger;
import com.flightapp.model.enums.BookingStatus;
import com.flightapp.model.enums.Gender;
import com.flightapp.model.enums.MealPreference;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BookingResponseTest {

    @Test
    void testAllFields() {
        Passenger p = new Passenger("John", Gender.MALE, 25, "A1", MealPreference.VEG);
        LocalDateTime now = LocalDateTime.now();

        BookingResponse res = new BookingResponse();
        res.setPnr("PNR001");
        res.setFlightId("FL1");
        res.setUserName("John");
        res.setEmail("john@example.com");
        res.setPassengers(List.of(p));
        res.setStatus(BookingStatus.CONFIRMED);
        res.setBookingDate(now);
        res.setTotalAmount(5000.0);
        res.setAirlineName("Test Airline");
        res.setFromLocation("Delhi");
        res.setToLocation("Mumbai");
        res.setDepartureTime(now.plusDays(1));

        assertEquals("PNR001", res.getPnr());
        assertEquals("FL1", res.getFlightId());
        assertEquals("John", res.getUserName());
        assertEquals("john@example.com", res.getEmail());
        assertEquals(1, res.getPassengers().size());
        assertEquals(BookingStatus.CONFIRMED, res.getStatus());
        assertEquals(now, res.getBookingDate());
        assertEquals(5000.0, res.getTotalAmount());
        assertEquals("Test Airline", res.getAirlineName());
        assertEquals("Delhi", res.getFromLocation());
        assertEquals("Mumbai", res.getToLocation());
        assertEquals(now.plusDays(1), res.getDepartureTime());
    }
    
    @Test
    void testAllArgsEqualsHashCodeToString() {
        Passenger p = new Passenger("John", Gender.MALE, 25, "A1", MealPreference.VEG);
        LocalDateTime now = LocalDateTime.now();

        BookingResponse r1 = new BookingResponse(
                "PNR001",
                "FL1",
                "John",
                "john@example.com",
                List.of(p),
                BookingStatus.CONFIRMED,
                now,
                5000.0,
                "Test Airline",
                "Delhi",
                "Mumbai",
                now.plusDays(1)
        );

        BookingResponse r2 = new BookingResponse(
                "PNR001",
                "FL1",
                "John",
                "john@example.com",
                List.of(p),
                BookingStatus.CONFIRMED,
                now,
                5000.0,
                "Test Airline",
                "Delhi",
                "Mumbai",
                now.plusDays(1)
        );

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());

        String s = r1.toString();
        assertTrue(s.contains("PNR001"));
        assertTrue(s.contains("Test Airline"));
    }
}
