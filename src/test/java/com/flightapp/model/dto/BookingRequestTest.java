package com.flightapp.model.dto;

import com.flightapp.model.entity.Passenger;
import com.flightapp.model.enums.Gender;
import com.flightapp.model.enums.MealPreference;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BookingRequestTest {

    @Test
    void testAllFields() {
        Passenger p = new Passenger("John", Gender.MALE, 25, "A1", MealPreference.VEG);

        BookingRequest req = new BookingRequest();
        req.setUserName("John Doe");
        req.setEmail("john@example.com");
        req.setPassengers(List.of(p));

        assertEquals("John Doe", req.getUserName());
        assertEquals("john@example.com", req.getEmail());
        assertEquals(1, req.getPassengers().size());
    }
    
    @Test
    void testAllArgsEqualsHashCodeToString() {
        Passenger p = new Passenger("John", Gender.MALE, 25, "A1", MealPreference.VEG);

        BookingRequest r1 = new BookingRequest("John Doe", "john@example.com", List.of(p));
        BookingRequest r2 = new BookingRequest("John Doe", "john@example.com", List.of(p));

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());

        String s = r1.toString();
        assertTrue(s.contains("John Doe"));
        assertTrue(s.contains("john@example.com"));
    }
}
