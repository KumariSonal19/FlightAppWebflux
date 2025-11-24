package com.flightapp.model.dto;

import com.flightapp.model.enums.TripType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class FlightSearchRequestTest {

    @Test
    void testAllFields() {
        LocalDate travelDate = LocalDate.now().plusDays(1);
        LocalDate returnDate = travelDate.plusDays(5);

        FlightSearchRequest req = new FlightSearchRequest();
        req.setFromLocation("Delhi");
        req.setToLocation("Goa");
        req.setTravelDate(travelDate);
        req.setTripType(TripType.ROUND_TRIP);
        req.setReturnDate(returnDate);

        assertEquals("Delhi", req.getFromLocation());
        assertEquals("Goa", req.getToLocation());
        assertEquals(travelDate, req.getTravelDate());
        assertEquals(TripType.ROUND_TRIP, req.getTripType());
        assertEquals(returnDate, req.getReturnDate());
    }
    
    @Test
    void testAllArgsEqualsHashCodeToString() {
        LocalDate travelDate = LocalDate.now().plusDays(1);
        LocalDate returnDate = travelDate.plusDays(5);

        FlightSearchRequest r1 = new FlightSearchRequest(
                "Delhi",
                "Goa",
                travelDate,
                TripType.ROUND_TRIP,
                returnDate
        );

        FlightSearchRequest r2 = new FlightSearchRequest(
                "Delhi",
                "Goa",
                travelDate,
                TripType.ROUND_TRIP,
                returnDate
        );

        assertEquals(r1, r2);
        assertEquals(r1.hashCode(), r2.hashCode());

        String s = r1.toString();
        assertTrue(s.contains("Delhi"));
        assertTrue(s.contains("Goa"));
    }
}
