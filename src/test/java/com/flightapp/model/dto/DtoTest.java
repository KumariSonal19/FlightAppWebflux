package com.flightapp.model.dto;

import com.flightapp.model.entity.Passenger;
import com.flightapp.model.enums.BookingStatus;
import com.flightapp.model.enums.Gender;
import com.flightapp.model.enums.MealPreference;
import com.flightapp.model.enums.TripType;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DtoTest {

    @Test
    void testApiResponseCoverage() {
        ApiResponse<String> success = ApiResponse.success("OK", "DATA");
        assertTrue(success.isSuccess());
        assertEquals("OK", success.getMessage());
        assertEquals("DATA", success.getData());
        assertNotNull(success.getTimestamp());

        ApiResponse<Object> error = ApiResponse.error("FAIL");
        assertFalse(error.isSuccess());
        assertEquals("FAIL", error.getMessage());
        assertNull(error.getData());
        assertNotNull(error.getTimestamp());
    }

    @Test
    void testBookingRequestCoverage() {
        Passenger p = new Passenger("John", Gender.MALE, 22, "C3", MealPreference.VEG);
        BookingRequest req = new BookingRequest("John", "john@mail.com", List.of(p));

        assertEquals("John", req.getUserName());
        assertEquals("john@mail.com", req.getEmail());
        assertEquals(1, req.getPassengers().size());
    }

    @Test
    void testBookingResponseCoverage() {
        BookingResponse res = new BookingResponse();
        res.setPnr("PNR001");
        res.setFlightId("F1");
        res.setUserName("John");
        res.setEmail("john@mail.com");
        res.setPassengers(List.of());
        res.setStatus(BookingStatus.CONFIRMED);
        res.setTotalAmount(1000.0);
        res.setAirlineName("AirAsia");
        res.setFromLocation("Delhi");
        res.setToLocation("Goa");

        assertEquals("PNR001", res.getPnr());
        assertEquals("F1", res.getFlightId());
        assertEquals("John", res.getUserName());
        assertEquals("john@mail.com", res.getEmail());
        assertEquals(BookingStatus.CONFIRMED, res.getStatus());
        assertEquals("AirAsia", res.getAirlineName());
        assertEquals("Delhi", res.getFromLocation());
        assertEquals("Goa", res.getToLocation());
    }

    @Test
    void testFlightSearchRequestCoverage() {
        FlightSearchRequest req = new FlightSearchRequest(
                "Delhi",
                "Mumbai",
                LocalDate.now(),
                TripType.ONE_WAY,
                null
        );

        assertEquals("Delhi", req.getFromLocation());
        assertEquals("Mumbai", req.getToLocation());
        assertEquals(TripType.ONE_WAY, req.getTripType());
        assertNull(req.getReturnDate());
    }
}
